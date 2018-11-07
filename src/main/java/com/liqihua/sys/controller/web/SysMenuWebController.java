package com.liqihua.sys.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.utils.Tool;
import com.liqihua.sys.entity.SysMenuEntity;
import com.liqihua.sys.entity.vo.SysMenuVO;
import com.liqihua.sys.service.SysMenuService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public WebResult save(@ApiParam(value = "pid",required = true) @RequestParam(value="pid",required = true)  Long pid,
                          @ApiParam(value = "title",required = true) @RequestParam(value="title",required = true)  String title,
                          @ApiParam(value = "routerName",required = true) @RequestParam(value="routerName",required = true)  String routerName,
                          @ApiParam(value = "level",required = true) @RequestParam(value="level",required = true)  Integer level,
                          @ApiParam(value = "hide",required = true) @RequestParam(value="hide",required = true)  Boolean hide,
                          @ApiParam(value = "rank",required = true) @RequestParam(value="rank",required = true)  Integer rank){
        SysMenuEntity entity = new SysMenuEntity();
        entity.setPid(pid);
        entity.setTitle(title);
        entity.setRouterName(routerName);
        entity.setLevel(level);
        entity.setHide(hide);
        entity.setRank(rank);
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
        List<SysMenuVO> voList = Tool.copyList(result.getRecords(),SysMenuVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
