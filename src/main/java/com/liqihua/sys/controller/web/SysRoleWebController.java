package com.liqihua.sys.controller.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.*;
import com.liqihua.sys.entity.vo.SysMenuVO;
import com.liqihua.sys.entity.vo.SysPermVO;
import com.liqihua.sys.entity.vo.SysRoleVO;
import com.liqihua.sys.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysRoleWebController")
public class SysRoleWebController extends BaseController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRolePermService sysRolePermService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysPermService sysPermService;


    @RequestMapping(value = "/setPerms", method = RequestMethod.POST)
    public WebResult setPerms(@RequestParam Long roleId,
                              String menuIds,
                              String permIds){
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("role_id",roleId));
        sysRolePermService.remove(new QueryWrapper<SysRolePermEntity>().eq("role_id",roleId));
        if(StrUtil.isNotBlank(menuIds)) {
            String[] menuIdArr = menuIds.split(",");
            for (String _menuId : menuIdArr) {
                if (StrUtil.isNotBlank(_menuId)) {
                    SysRoleMenuEntity rm = new SysRoleMenuEntity(roleId, Long.valueOf(_menuId));
                    sysRoleMenuService.save(rm);
                }
            }
            if(StrUtil.isNotBlank(permIds)){
                String[] permIdArr = permIds.split(",");
                for(String _permId : permIdArr){
                    if(StrUtil.isNotBlank(_permId)){
                        SysRolePermEntity rp = new SysRolePermEntity();
                        rp.setRoleId(roleId);
                        rp.setPermId(Long.valueOf(_permId));
                        sysRolePermService.save(rp);
                    }
                }
            }
        }
        return buildSuccessInfo(null);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(Long id,
                          @RequestParam String name,
                          String remarks){
        SysRoleEntity entity = null;
        if(id != null){
            entity = sysRoleService.getById(id);
        }else{
            entity = new SysRoleEntity();
        }
        entity.setName(name);
        entity.setRemarks(remarks);
        sysRoleService.saveOrUpdate(entity);
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@RequestParam Long id){
        boolean delete = sysRoleService.removeById(id);
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("role_id",id));
        sysRolePermService.remove(new QueryWrapper<SysRolePermEntity>().eq("role_id",id));
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@RequestParam Long id){
        SysRoleEntity entity = sysRoleService.getById(id);
        if(entity == null){
            return buildFailedInfo(ApiConstant.PARAM_ERROR);
        }
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(entity,vo);
        vo = makeVO(vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize){
        IPage result = sysRoleService.page(new Page<SysRoleEntity>(page,pageSize),null);
        List<SysRoleVO> voList = SysBeanUtil.copyList(result.getRecords(),SysRoleVO.class);
        if(voList != null){
            voList.forEach(vo -> {
                vo = makeVO(vo);
            });
        }
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }


    public SysRoleVO makeVO(SysRoleVO vo){
        if(vo != null && vo.getId() != null) {
            List<SysRoleMenuEntity> rmList = sysRoleMenuService.list(new QueryWrapper<SysRoleMenuEntity>().eq("role_id", vo.getId()));
            List<SysRolePermEntity> rpList = sysRolePermService.list(new QueryWrapper<SysRolePermEntity>().eq("role_id", vo.getId()));
            if (rmList != null && rmList.size() > 0) {
                List<Long> menuIdList = rmList.stream().map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList());
                List<SysMenuEntity> menuList = sysMenuService.list(new QueryWrapper<SysMenuEntity>().in("id", menuIdList).orderByAsc("level", "id"));
                List<SysMenuVO> menuVOList = SysBeanUtil.copyList(menuList, SysMenuVO.class);
                vo.setMenuList(menuVOList);
            }
            if (rpList != null && rpList.size() > 0) {
                List<Long> permIdList = rpList.stream().map(SysRolePermEntity::getPermId).collect(Collectors.toList());
                List<SysPermEntity> permList = sysPermService.list(new QueryWrapper<SysPermEntity>().in("id", permIdList));
                List<SysPermVO> permVOList = SysBeanUtil.copyList(permList, SysPermVO.class);
                vo.setPermList(permVOList);
            }
        }
        return vo;
    }

}
