package com.liqihua.sys.controller.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.SysPermEntity;
import com.liqihua.sys.entity.vo.SysPermVO;
import com.liqihua.sys.service.SysPermService;
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
    public WebResult save(@RequestParam String name,
                          @RequestParam String symbol,
                          String remarks){
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
    public WebResult delete(@RequestParam Long id){
        boolean delete = sysPermService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@RequestParam Long id){
        SysPermEntity entity = sysPermService.getById(id);
        SysPermVO vo = null;
        if(entity != null){
            vo = new SysPermVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize,
                          String name,
                          String symbol){
        QueryWrapper queryWrapper = new QueryWrapper<SysPermEntity>();
        if(StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        if(StrUtil.isNotBlank(symbol)) {
            queryWrapper.like("symbol", symbol);
        }
        IPage result = sysPermService.page(new Page<SysPermEntity>(page,pageSize),queryWrapper);
        List<SysPermVO> voList = SysBeanUtil.copyList(result.getRecords(),SysPermVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
