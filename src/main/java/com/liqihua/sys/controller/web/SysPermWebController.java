package com.liqihua.sys.controller.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.SysMenuEntity;
import com.liqihua.sys.entity.SysPermEntity;
import com.liqihua.sys.entity.SysPermMenuEntity;
import com.liqihua.sys.entity.SysRolePermEntity;
import com.liqihua.sys.entity.vo.SysMenuVO;
import com.liqihua.sys.entity.vo.SysPermMenuVO;
import com.liqihua.sys.entity.vo.SysPermVO;
import com.liqihua.sys.service.SysMenuService;
import com.liqihua.sys.service.SysPermMenuService;
import com.liqihua.sys.service.SysPermService;
import com.liqihua.sys.service.SysRolePermService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysPermWebController")
public class SysPermWebController extends BaseController {
    @Resource
    private SysPermService sysPermService;
    @Resource
    private SysPermMenuService sysPermMenuService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysRolePermService sysRolePermService;


    @RequestMapping(value = "/getByMenuIds", method = RequestMethod.GET)
    public WebResult getByMenuIds(@RequestParam String menuIds){
        String[] arr = menuIds.split(",");
        List<Long> menuIdList = new LinkedList<>();
        if(arr != null && arr.length > 0){
            for(String _menuId : arr) {
                menuIdList.add(Long.valueOf(_menuId));
            }
        }
        List<SysPermVO> voList = new LinkedList<>();
        List<SysPermMenuEntity> pmList = sysPermMenuService.list(new QueryWrapper<SysPermMenuEntity>().in("menu_id",menuIdList).orderByAsc("menu_id"));
        if(pmList != null){
            pmList.forEach(pm -> {
                SysPermEntity perm = sysPermService.getById(pm.getPermId());
                if(perm != null){
                    SysPermVO vo = new SysPermVO();
                    BeanUtils.copyProperties(perm,vo);
                    voList.add(vo);
                }
            });
        }
        return buildSuccessInfo(voList);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(Long id,
                          @RequestParam Long menuId,
                          @RequestParam String name,
                          @RequestParam String symbol,
                          String remarks){
        SysPermEntity entity = null;
        if(id != null) {
            entity = sysPermService.getById(id);
            if(entity == null) {
                return buildFailedInfo(ApiConstant.PARAM_ERROR);
            }
        }else{
            entity = new SysPermEntity();
        }
        entity.setName(name);
        entity.setSymbol(symbol);
        entity.setRemarks(remarks);
        sysPermService.saveOrUpdate(entity);
        sysPermMenuService.remove(new QueryWrapper<SysPermMenuEntity>().eq("perm_id",entity.getId()));
        SysPermMenuEntity pm = new SysPermMenuEntity(menuId,entity.getId());
        sysPermMenuService.save(pm);
        SysPermVO vo = new SysPermVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@RequestParam Long id){
        int count = sysRolePermService.count(new QueryWrapper<SysRolePermEntity>().eq("perm_id",id));
        if(count > 0){
            return buildFailedInfo("有角色绑定了该权限，请先解绑");
        }
        boolean delete = sysPermService.removeById(id);
        sysPermMenuService.remove(new QueryWrapper<SysPermMenuEntity>().eq("perm_id",id));
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@RequestParam Long id){
        SysPermEntity entity = sysPermService.getById(id);
        SysPermVO vo = null;
        if(entity != null){
            vo = new SysPermVO();
            BeanUtils.copyProperties(entity,vo);
            SysPermMenuEntity pm = sysPermMenuService.getOne(new QueryWrapper<SysPermMenuEntity>().eq("perm_id",entity.getId()));
            if(pm != null){
                SysMenuEntity menu = sysMenuService.getById(pm.getMenuId());
                if(menu != null){
                    SysMenuVO menuVO = new SysMenuVO();
                    BeanUtils.copyProperties(menu,menuVO);
                    vo.setMenu(menuVO);
                }
            }
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize){
        IPage result = sysPermMenuService.page(new Page<SysPermMenuEntity>(page,pageSize),new QueryWrapper<SysPermMenuEntity>().orderByAsc("menu_id"));
        List<SysPermMenuEntity> pmList = result.getRecords();
        List<SysPermMenuVO> voList = SysBeanUtil.copyList(pmList,SysPermMenuVO.class);
        if(voList != null){
            voList.forEach(vo -> {
                SysMenuEntity menu = sysMenuService.getById(vo.getMenuId());
                SysPermEntity perm = sysPermService.getById(vo.getPermId());
                SysMenuVO menuVO = new SysMenuVO();
                SysPermVO permVO = new SysPermVO();
                if(menu != null) {
                    BeanUtils.copyProperties(menu, menuVO);
                }
                if(perm != null) {
                    BeanUtils.copyProperties(perm, permVO);
                }
                vo.setMenu(menuVO);
                vo.setPerm(permVO);
            });
        }
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
