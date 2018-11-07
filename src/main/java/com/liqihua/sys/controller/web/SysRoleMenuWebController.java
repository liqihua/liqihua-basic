package com.liqihua.sys.controller.web;

import com.liqihua.common.utils.Tool;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import com.liqihua.common.basic.WebResult;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import com.liqihua.sys.entity.SysRoleMenuEntity;
import com.liqihua.sys.entity.vo.SysRoleMenuVO;
import com.liqihua.sys.service.SysRoleMenuService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * <p>
 * 角色菜单树形关系 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysRoleMenuWebController")
public class SysRoleMenuWebController extends BaseController {
    @Resource
    private SysRoleMenuService sysRoleMenuService;






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "roleId",required = true) @RequestParam(value="roleId",required = true)  Long roleId,
                          @ApiParam(value = "menuId",required = true) @RequestParam(value="menuId",required = true)  Long menuId
                          ){
        SysRoleMenuEntity entity = new SysRoleMenuEntity();
        entity.setRoleId(roleId);
        entity.setMenuId(menuId);
        sysRoleMenuService.saveOrUpdate(entity);
        SysRoleMenuVO vo = new SysRoleMenuVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysRoleMenuService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysRoleMenuEntity entity = sysRoleMenuService.getById(id);
        SysRoleMenuVO vo = null;
        if(entity != null){
            vo = new SysRoleMenuVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "roleId",required = false) @RequestParam(value="roleId",required = false)  Long roleId,
                          @ApiParam(value = "menuId",required = false) @RequestParam(value="menuId",required = false)  Long menuId){
        SysRoleMenuEntity entity = new SysRoleMenuEntity();
        entity.setRoleId(roleId);
        entity.setMenuId(menuId);
        QueryWrapper queryWrapper = new QueryWrapper<SysRoleMenuEntity>(entity);
        IPage result = sysRoleMenuService.page(new Page<SysRoleMenuEntity>(page,pageSize),queryWrapper);
        List<SysRoleMenuVO> voList = Tool.copyList(result.getRecords(),SysRoleMenuVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
