package com.liqihua.sys.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.SysMenuEntity;
import com.liqihua.sys.entity.vo.SysMenuVO;
import com.liqihua.sys.service.SysMenuService;
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
    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@RequestParam  String title,
                          @RequestParam String routerName,
                          @RequestParam Boolean hide,
                          Long id,
                          Long pid,
                          Integer rank){
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
        entity.setRank(rank);

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
        SysMenuVO vo = new SysMenuVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@RequestParam Long id){
        SysMenuEntity menu = sysMenuService.getById(id);
        if(menu == null){
            return buildFailedInfo(ApiConstant.PARAM_ERROR);
        }
        List<SysMenuEntity> children = sysMenuService.list(new QueryWrapper<SysMenuEntity>().eq("pid",menu.getId()));
        if(children != null){
            deleteChildren(children);
        }
        boolean delete = sysMenuService.removeById(id);
        return buildSuccessInfo(delete);
    }




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



    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize,
                          Long pid,
                          String title,
                          String routerName,
                          Integer level,
                          Boolean hide,
                          Integer rank){
        SysMenuEntity entity = new SysMenuEntity();
        entity.setPid(pid);
        entity.setTitle(title);
        entity.setRouterName(routerName);
        entity.setLevel(level);
        entity.setHide(hide);
        entity.setRank(rank);
        QueryWrapper queryWrapper = new QueryWrapper<SysMenuEntity>(entity);
        IPage result = sysMenuService.page(new Page<SysMenuEntity>(page,pageSize),queryWrapper);
        List<SysMenuVO> voList = SysBeanUtil.copyList(result.getRecords(),SysMenuVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }



    @RequestMapping(value = "/getTree", method = RequestMethod.GET)
    public WebResult getTree(){
        List<SysMenuEntity> list = sysMenuService.list(null);
        List<SysMenuVO> voList = SysBeanUtil.copyList(list,SysMenuVO.class);
        List<SysMenuVO> tree = null;
        if(voList != null){
            tree = voList.stream().filter(vo -> 1 == vo.getLevel()).sorted((vo1,vo2) -> vo1.getRank() - vo2.getRank()).collect(Collectors.toList());
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
            List<SysMenuVO> children = voList.stream().filter(vo -> parent.getId().equals(vo.getPid())).sorted((vo1,vo2) -> vo1.getRank() - vo2.getRank()).collect(Collectors.toList());
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
