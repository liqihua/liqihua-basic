package com.liqihua.sys.controller.api;

import com.liqihua.common.utils.Tool;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import com.liqihua.sys.entity.SysUserEntity;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.SysUserService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * <p>
 *  用户 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysUserWebController")
public class SysUserWebController extends BaseController {
    @Resource
    private SysUserService sysUserService;






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "username",required = true) @RequestParam(value="username",required = true)  String username,
                          @ApiParam(value = "password",required = true) @RequestParam(value="password",required = true)  String password,
                          @ApiParam(value = "nickname",required = true) @RequestParam(value="nickname",required = true)  String nickname,
                          @ApiParam(value = "realName",required = true) @RequestParam(value="realName",required = true)  String realName,
                          @ApiParam(value = "gender",required = true) @RequestParam(value="gender",required = true)  Boolean gender,
                          @ApiParam(value = "avatar",required = true) @RequestParam(value="avatar",required = true)  String avatar,
                          @ApiParam(value = "mobile",required = true) @RequestParam(value="mobile",required = true)  String mobile,
                          @ApiParam(value = "remarks",required = true) @RequestParam(value="remarks",required = true)  String remarks,
                          @ApiParam(value = "lock",required = true) @RequestParam(value="lock",required = true)  Boolean lock
                          ){
        SysUserEntity entity = new SysUserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setNickname(nickname);
        entity.setRealName(realName);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setMobile(mobile);
        entity.setRemarks(remarks);
        entity.setLock(lock);
        sysUserService.saveOrUpdate(entity);
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysUserService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysUserEntity entity = sysUserService.getById(id);
        SysUserVO vo = null;
        if(entity != null){
            vo = new SysUserVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "username",required = false) @RequestParam(value="username",required = false)  String username,
                          @ApiParam(value = "password",required = false) @RequestParam(value="password",required = false)  String password,
                          @ApiParam(value = "nickname",required = false) @RequestParam(value="nickname",required = false)  String nickname,
                          @ApiParam(value = "realName",required = false) @RequestParam(value="realName",required = false)  String realName,
                          @ApiParam(value = "gender",required = false) @RequestParam(value="gender",required = false)  Boolean gender,
                          @ApiParam(value = "avatar",required = false) @RequestParam(value="avatar",required = false)  String avatar,
                          @ApiParam(value = "mobile",required = false) @RequestParam(value="mobile",required = false)  String mobile,
                          @ApiParam(value = "remarks",required = false) @RequestParam(value="remarks",required = false)  String remarks,
                          @ApiParam(value = "lock",required = false) @RequestParam(value="lock",required = false)  Boolean lock){
        SysUserEntity entity = new SysUserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setNickname(nickname);
        entity.setRealName(realName);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setMobile(mobile);
        entity.setRemarks(remarks);
        entity.setLock(lock);
        QueryWrapper queryWrapper = new QueryWrapper<SysUserEntity>(entity);
        IPage result = sysUserService.page(new Page<SysUserEntity>(page,pageSize),queryWrapper);
        List<SysUserVO> voList = Tool.copyList(result.getRecords(),SysUserVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
