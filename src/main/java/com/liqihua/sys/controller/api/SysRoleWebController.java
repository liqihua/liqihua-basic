package com.liqihua.sys.controller.api;

import com.liqihua.common.utils.Tool;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import com.liqihua.sys.entity.SysRoleEntity;
import com.liqihua.sys.entity.vo.SysRoleVO;
import com.liqihua.sys.service.SysRoleService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

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
    public WebResult save(@ApiParam(value = "name",required = true) @RequestParam(value="name",required = true)  String name,
                          @ApiParam(value = "remarks",required = true) @RequestParam(value="remarks",required = true)  String remarks
                          ){
        SysRoleEntity entity = new SysRoleEntity();
        entity.setName(name);
        entity.setRemarks(remarks);
        sysRoleService.saveOrUpdate(entity);
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysRoleService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysRoleEntity entity = sysRoleService.getById(id);
        SysRoleVO vo = null;
        if(entity != null){
            vo = new SysRoleVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "name",required = false) @RequestParam(value="name",required = false)  String name,
                          @ApiParam(value = "remarks",required = false) @RequestParam(value="remarks",required = false)  String remarks){
        SysRoleEntity entity = new SysRoleEntity();
        entity.setName(name);
        entity.setRemarks(remarks);
        QueryWrapper queryWrapper = new QueryWrapper<SysRoleEntity>(entity);
        IPage result = sysRoleService.page(new Page<SysRoleEntity>(page,pageSize),queryWrapper);
        List<SysRoleVO> voList = Tool.copyList(result.getRecords(),SysRoleVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
