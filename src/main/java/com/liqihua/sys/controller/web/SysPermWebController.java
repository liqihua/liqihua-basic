package com.liqihua.sys.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.SysPermEntity;
import com.liqihua.sys.entity.vo.SysPermVO;
import com.liqihua.sys.service.SysPermService;
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






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "name",required = true) @RequestParam(value="name",required = true)  String name,
                          @ApiParam(value = "symbol",required = true) @RequestParam(value="symbol",required = true)  String symbol,
                          @ApiParam(value = "remarks",required = true) @RequestParam(value="remarks",required = true)  String remarks
                          ){
        SysPermEntity entity = new SysPermEntity();
        entity.setName(name);
        entity.setSymbol(symbol);
        entity.setRemarks(remarks);
        sysPermService.saveOrUpdate(entity);
        SysPermVO vo = new SysPermVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysPermService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysPermEntity entity = sysPermService.getById(id);
        SysPermVO vo = null;
        if(entity != null){
            vo = new SysPermVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "name",required = false) @RequestParam(value="name",required = false)  String name,
                          @ApiParam(value = "symbol",required = false) @RequestParam(value="symbol",required = false)  String symbol,
                          @ApiParam(value = "remarks",required = false) @RequestParam(value="remarks",required = false)  String remarks){
        SysPermEntity entity = new SysPermEntity();
        entity.setName(name);
        entity.setSymbol(symbol);
        entity.setRemarks(remarks);
        QueryWrapper queryWrapper = new QueryWrapper<SysPermEntity>(entity);
        IPage result = sysPermService.page(new Page<SysPermEntity>(page,pageSize),queryWrapper);
        List<SysPermVO> voList = SysBeanUtil.copyList(result.getRecords(),SysPermVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
