package com.liqihua.sys.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.SysRoleEntity;
import com.liqihua.sys.entity.vo.SysRoleVO;
import com.liqihua.sys.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@RequestParam String name,
                          String remarks){
        SysRoleEntity entity = new SysRoleEntity();
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
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@RequestParam Long id){
        SysRoleEntity entity = sysRoleService.getById(id);
        SysRoleVO vo = null;
        if(entity != null){
            vo = new SysRoleVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize){
        IPage result = sysRoleService.page(new Page<SysRoleEntity>(page,pageSize),null);
        List<SysRoleVO> voList = SysBeanUtil.copyList(result.getRecords(),SysRoleVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
