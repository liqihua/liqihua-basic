package com.liqihua.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.modules.sys.entity.SysMenuEntity;
import com.liqihua.modules.sys.entity.SysPermMenuEntity;
import com.liqihua.modules.sys.entity.SysRoleMenuEntity;
import com.liqihua.modules.sys.entity.vo.SysMenuVO;
import com.liqihua.modules.sys.service.SysMenuService;
import com.liqihua.modules.sys.service.SysPermMenuService;
import com.liqihua.modules.sys.service.SysRoleMenuService;
import com.liqihua.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 菜单 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysMenuWebController")
public class SysMenuWebController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysMenuWebController.class);


    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysPermMenuService sysPermMenuService;

    @RequiresPermissions("sysMenu-save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@RequestParam  String title,
                          @RequestParam String routerName,
                          @RequestParam Boolean hide,
                          Long id,
                          Long pid,
                          Integer rankNum){
        SysMenuEntity entity = null;
        if(id != null){
            entity = sysMenuService.getById(id);
            if(entity == null){
                return buildFailedInfo(ApiConstant.PARAM_ERROR);
            }
        }else {
            entity = new SysMenuEntity();
        }
        entity.setPid(pid);
        entity.setTitle(title);
        entity.setRouterName(routerName);
        entity.setHide(hide);
        entity.setRankNum(rankNum);

        /**
         * 计算菜单级别，默认一级菜单
         */
        int level = 1;
        if(pid != null){
            if(id != null){
                if(id.equals(pid)) {
                    return buildFailedInfo("不能选当前菜单为父级菜单");
                }
                SysMenuEntity parent = sysMenuService.getById(pid);
                if(parent.getLevel() > entity.getLevel()){
                    return buildFailedInfo("父菜单级别不能低于当前菜单");
                }
            }
            Long tempPid = pid;
            while (tempPid != null){
                level += 1;
                QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id",tempPid);
                queryWrapper.select("pid");
                SysMenuEntity parent = sysMenuService.getOne(queryWrapper);
                if(parent != null){
                    tempPid = parent.getPid();
                }else{
                    tempPid = null;
                }
            }
        }
        entity.setLevel(level);
        sysMenuService.saveOrUpdate(entity);
        /**
         * 刷新用户菜单列表
         */
        sysUserService.refreshUserMenu();
        SysMenuVO vo = new SysMenuVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }


    @RequiresPermissions("sysMenu-delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@RequestParam Long id){
        int count = sysRoleMenuService.count(new QueryWrapper<SysRoleMenuEntity>().eq("menu_id",id));
        if(count > 0){
            return buildFailedInfo("有角色绑定了该菜菜单，请先解绑");
        }
        count = sysPermMenuService.count(new QueryWrapper<SysPermMenuEntity>().eq("menu_id",id));
        if(count > 0) {
            return buildFailedInfo("该菜单下有权限未删除，请先该删除属于该菜单的权限");
        }
        count = sysMenuService.count(new QueryWrapper<SysMenuEntity>().eq("pid",id));
        if(count > 0){
            return buildFailedInfo("该菜单下还有子菜单，请先删除属于该菜单的子菜单");
        }
        SysMenuEntity menu = sysMenuService.getById(id);
        if(menu == null){
            return buildFailedInfo(ApiConstant.PARAM_ERROR);
        }
        /*List<SysMenuEntity> children = sysMenuService.list(new QueryWrapper<SysMenuEntity>().eq("pid",menu.getId()));
        if(children != null){
            deleteChildren(children);
        }*/
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("menu_id",id));
        sysPermMenuService.remove(new QueryWrapper<SysPermMenuEntity>().eq("menu_id",id));
        boolean delete = sysMenuService.removeById(id);
        return buildSuccessInfo(delete);
    }

    @RequiresPermissions("sysMenu-list")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize,
                          Long pid,
                          String title,
                          String routerName,
                          Integer level,
                          Boolean hide,
                          Integer rankNum){
        SysMenuEntity entity = new SysMenuEntity();
        entity.setPid(pid);
        entity.setTitle(title);
        entity.setRouterName(routerName);
        entity.setLevel(level);
        entity.setHide(hide);
        entity.setRankNum(rankNum);
        QueryWrapper queryWrapper = new QueryWrapper<SysMenuEntity>(entity);
        IPage result = sysMenuService.page(new Page<SysMenuEntity>(page,pageSize),queryWrapper);
        List<SysMenuVO> voList = SysBeanUtil.copyList(result.getRecords(),SysMenuVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }



    @RequiresAuthentication
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@RequestParam Long id){
        SysMenuEntity entity = sysMenuService.getById(id);
        SysMenuVO vo = null;
        if(entity != null){
            vo = new SysMenuVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }





    @RequiresAuthentication
    @RequestMapping(value = "/getTree", method = RequestMethod.GET)
    public WebResult getTree(){
        List<SysMenuEntity> list = sysMenuService.list(new QueryWrapper<SysMenuEntity>().eq("hide",false));
        List<SysMenuVO> voList = SysBeanUtil.copyList(list,SysMenuVO.class);
        List<SysMenuVO> tree = null;
        if(voList != null){
            tree = voList.stream().filter(vo -> 1 == vo.getLevel()).sorted((vo1,vo2) -> vo1.getRankNum() - vo2.getRankNum()).collect(Collectors.toList());
            tree = makeTree(tree,voList);
        }
        return buildSuccessInfo(tree);
    }




    /**
     * 递归组装树形菜单
     * @param parentList
     * @param voList
     * @return
     */
    public List<SysMenuVO> makeTree(List<SysMenuVO> parentList,List<SysMenuVO> voList){
        parentList.forEach(parent -> {
            List<SysMenuVO> children = voList.stream().filter(vo -> parent.getId().equals(vo.getPid())).sorted((vo1,vo2) -> vo1.getRankNum() - vo2.getRankNum()).collect(Collectors.toList());
            if(children != null){
                children = makeTree(children,voList);
                parent.setChildren(children);
            }
        });
        return parentList;
    }

    /**
     * 递归删除所有子菜单
     * @param list
     */
    public void deleteChildren(List<SysMenuEntity> list){
        if(list != null){
            list.forEach(menu -> {
                List<SysMenuEntity> children = sysMenuService.list(new QueryWrapper<SysMenuEntity>().eq("pid",menu.getId()));
                if(children != null){
                    deleteChildren(children);
                }
                sysMenuService.removeById(menu);
            });
        }
    }


}
