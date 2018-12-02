package com.liqihua.sys.controller.web;

import com.alibaba.fastjson.JSON;
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
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import javax.annotation.Resource;
import java.util.*;
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

    /*public static void main(String[] args) {
        SysMenuVO vo1 = new SysMenuVO();
        SysMenuVO vo2 = new SysMenuVO();
        SysMenuVO vo3 = new SysMenuVO();
        SysMenuVO vo4 = new SysMenuVO();
        vo1.setId(1L);
        vo2.setId(2L);
        vo3.setId(3L);
        vo4.setId(4L);
        vo2.setPid(1L);
        vo3.setPid(2L);
        vo4.setPid(3L);
        vo1.setLevel(1);
        vo2.setLevel(2);
        vo3.setLevel(3);
        vo4.setLevel(4);
        vo1.setTitle("aa");
        vo2.setTitle("bb");
        vo3.setTitle("cc");
        vo4.setTitle("dd");
        List<SysMenuVO> voList = new LinkedList<>();
        voList.add(vo1);
        voList.add(vo2);
        voList.add(vo3);
        voList.add(vo4);

        *//*List<Integer> levelList = voList.stream().map(SysMenuVO::getLevel).collect(Collectors.toList());
        levelList.forEach(level -> {
            System.out.println(level);
        });
        levelList = levelList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o))), LinkedList::new));
        levelList.forEach(level -> {
            System.out.println(level);
        });*//*

        // List<SysMenuVO> newList = voList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getLevel()))), ArrayList::new));
        voList = new LinkedList<>();
        List<SysMenuVO> tree = voList.stream().filter(vo -> 1 == vo.getLevel()).collect(Collectors.toList());
        tree = makeTree(tree,voList);
        System.out.println(JSON.toJSONString(tree));
    }*/

    @RequestMapping(value = "/getTree", method = RequestMethod.GET)
    public WebResult getTree(){
        List<SysMenuEntity> list = sysMenuService.list(null);
        List<SysMenuVO> voList = SysBeanUtil.copyList(list,SysMenuVO.class);
        List<SysMenuVO> tree = null;
        if(voList != null){
            tree = voList.stream().filter(vo -> 1 == vo.getLevel()).collect(Collectors.toList());
            tree = makeTree(tree,voList);
        }
        return buildSuccessInfo(tree);
    }





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
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysMenuService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysMenuEntity entity = sysMenuService.getById(id);
        SysMenuVO vo = null;
        if(entity != null){
            vo = new SysMenuVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "pid",required = false) @RequestParam(value="pid",required = false)  Long pid,
                          @ApiParam(value = "title",required = false) @RequestParam(value="title",required = false)  String title,
                          @ApiParam(value = "routerName",required = false) @RequestParam(value="routerName",required = false)  String routerName,
                          @ApiParam(value = "level",required = false) @RequestParam(value="level",required = false)  Integer level,
                          @ApiParam(value = "hide",required = false) @RequestParam(value="hide",required = false)  Boolean hide,
                          @ApiParam(value = "rank",required = false) @RequestParam(value="rank",required = false)  Integer rank){
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

    /**
     * 递归组装树形菜单
     * @param parentList
     * @param voList
     * @return
     */
    public List<SysMenuVO> makeTree(List<SysMenuVO> parentList,List<SysMenuVO> voList){
        parentList.forEach(parent -> {
            List<SysMenuVO> children = voList.stream().filter(vo -> parent.getId() == vo.getPid()).collect(Collectors.toList());
            if(children != null){
                children = makeTree(children,voList);
                parent.setChildren(children);
            }
        });
        return parentList;
    }

}
