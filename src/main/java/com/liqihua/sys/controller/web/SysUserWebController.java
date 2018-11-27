package com.liqihua.sys.controller.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.common.utils.SysFileUtil;
import com.liqihua.sys.entity.SysUserEntity;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.SysUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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


    @Value("${mvc.static.prefix}")
    private String prefix;


    @Resource
    private SysUserService sysUserService;






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "id",required = false) @RequestParam(value="id",required = false) Long id,
                          @ApiParam(value = "username",required = true) @RequestParam(value="username",required = true)  String username,
                          @ApiParam(value = "password",required = true) @RequestParam(value="password",required = true)  String password,
                          @ApiParam(value = "nickname",required = true) @RequestParam(value="nickname",required = true)  String nickname,
                          @ApiParam(value = "realName",required = true) @RequestParam(value="realName",required = true)  String realName,
                          @ApiParam(value = "gender",required = true) @RequestParam(value="gender",required = true)  Boolean gender,
                          @ApiParam(value = "avatar",required = false) @RequestParam(value="avatar",required = false)  String avatar,
                          @ApiParam(value = "mobile",required = true) @RequestParam(value="mobile",required = true)  String mobile,
                          @ApiParam(value = "remarks",required = true) @RequestParam(value="remarks",required = true)  String remarks,
                          @ApiParam(value = "locked",required = true) @RequestParam(value="locked",required = true)  Boolean locked){
        SysUserEntity entity = null;
        if(id != null){
            entity = sysUserService.getById(id);
            if(entity == null){
                return buildFailedInfo(ApiConstant.USER_NOT_EXIST);
            }
        }else{
            entity = new SysUserEntity();
        }
        if(StrUtil.isNotBlank(avatar)){
            avatar = avatar.replace(prefix,"");
        }
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setNickname(nickname);
        entity.setRealName(realName);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setMobile(mobile);
        entity.setRemarks(remarks);
        entity.setLocked(locked);
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
            if(StrUtil.isNotBlank(vo.getAvatar()) && vo.getAvatar().startsWith("/")){
                vo.setAvatar(prefix + vo.getAvatar());
            }
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.GET)
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
        entity.setLocked(lock);
        QueryWrapper queryWrapper = new QueryWrapper<SysUserEntity>(entity);
        IPage result = sysUserService.page(new Page<SysUserEntity>(page,pageSize),queryWrapper);
        List<SysUserVO> voList = SysBeanUtil.copyList(result.getRecords(),SysUserVO.class);
        voList.forEach(vo -> {
            if(StrUtil.isNotBlank(vo.getAvatar()) && vo.getAvatar().startsWith("/")){
                vo.setAvatar(prefix + vo.getAvatar());
            }
        });
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }



    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public WebResult uploadAvatar(@RequestParam(required = true) MultipartFile avatar){
        String path = SysFileUtil.uploadFile(avatar,null);
        return buildSuccessInfo(prefix + path);
    }



}
