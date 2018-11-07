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
import com.liqihua.sys.entity.SysRoleUserEntity;
import com.liqihua.sys.entity.vo.SysRoleUserVO;
import com.liqihua.sys.service.SysRoleUserService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * <p>
 * 用户角色关系 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysRoleUserWebController")
public class SysRoleUserWebController extends BaseController {
    @Resource
    private SysRoleUserService sysRoleUserService;






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "roleId",required = true) @RequestParam(value="roleId",required = true)  Long roleId,
                          @ApiParam(value = "userId",required = true) @RequestParam(value="userId",required = true)  Long userId
                          ){
        SysRoleUserEntity entity = new SysRoleUserEntity();
        entity.setRoleId(roleId);
        entity.setUserId(userId);
        sysRoleUserService.saveOrUpdate(entity);
        SysRoleUserVO vo = new SysRoleUserVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysRoleUserService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysRoleUserEntity entity = sysRoleUserService.getById(id);
        SysRoleUserVO vo = null;
        if(entity != null){
            vo = new SysRoleUserVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "roleId",required = false) @RequestParam(value="roleId",required = false)  Long roleId,
                          @ApiParam(value = "userId",required = false) @RequestParam(value="userId",required = false)  Long userId){
        SysRoleUserEntity entity = new SysRoleUserEntity();
        entity.setRoleId(roleId);
        entity.setUserId(userId);
        QueryWrapper queryWrapper = new QueryWrapper<SysRoleUserEntity>(entity);
        IPage result = sysRoleUserService.page(new Page<SysRoleUserEntity>(page,pageSize),queryWrapper);
        List<SysRoleUserVO> voList = Tool.copyList(result.getRecords(),SysRoleUserVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
