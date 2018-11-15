package com.liqihua.sys.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.SysPermMenuEntity;
import com.liqihua.sys.entity.vo.SysPermMenuVO;
import com.liqihua.sys.service.SysPermMenuService;
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
 * 菜单权限树形关系 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysPermMenuWebController")
public class SysPermMenuWebController extends BaseController {
    @Resource
    private SysPermMenuService sysPermMenuService;






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "menuId",required = true) @RequestParam(value="menuId",required = true)  Long menuId,
                          @ApiParam(value = "permId",required = true) @RequestParam(value="permId",required = true)  Long permId
                          ){
        SysPermMenuEntity entity = new SysPermMenuEntity();
        entity.setMenuId(menuId);
        entity.setPermId(permId);
        sysPermMenuService.saveOrUpdate(entity);
        SysPermMenuVO vo = new SysPermMenuVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysPermMenuService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysPermMenuEntity entity = sysPermMenuService.getById(id);
        SysPermMenuVO vo = null;
        if(entity != null){
            vo = new SysPermMenuVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "menuId",required = false) @RequestParam(value="menuId",required = false)  Long menuId,
                          @ApiParam(value = "permId",required = false) @RequestParam(value="permId",required = false)  Long permId){
        SysPermMenuEntity entity = new SysPermMenuEntity();
        entity.setMenuId(menuId);
        entity.setPermId(permId);
        QueryWrapper queryWrapper = new QueryWrapper<SysPermMenuEntity>(entity);
        IPage result = sysPermMenuService.page(new Page<SysPermMenuEntity>(page,pageSize),queryWrapper);
        List<SysPermMenuVO> voList = SysBeanUtil.copyList(result.getRecords(),SysPermMenuVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
