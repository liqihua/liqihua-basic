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
import com.liqihua.sys.entity.SysRolePermEntity;
import com.liqihua.sys.entity.vo.SysRolePermVO;
import com.liqihua.sys.service.SysRolePermService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * <p>
 * 角色权限树形关系 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysRolePermWebController")
public class SysRolePermWebController extends BaseController {
    @Resource
    private SysRolePermService sysRolePermService;






    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(@ApiParam(value = "roleId",required = true) @RequestParam(value="roleId",required = true)  Long roleId,
                          @ApiParam(value = "permId",required = true) @RequestParam(value="permId",required = true)  Long permId
                          ){
        SysRolePermEntity entity = new SysRolePermEntity();
        entity.setRoleId(roleId);
        entity.setPermId(permId);
        sysRolePermService.saveOrUpdate(entity);
        SysRolePermVO vo = new SysRolePermVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = sysRolePermService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        SysRolePermEntity entity = sysRolePermService.getById(id);
        SysRolePermVO vo = null;
        if(entity != null){
            vo = new SysRolePermVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "roleId",required = false) @RequestParam(value="roleId",required = false)  Long roleId,
                          @ApiParam(value = "permId",required = false) @RequestParam(value="permId",required = false)  Long permId){
        SysRolePermEntity entity = new SysRolePermEntity();
        entity.setRoleId(roleId);
        entity.setPermId(permId);
        QueryWrapper queryWrapper = new QueryWrapper<SysRolePermEntity>(entity);
        IPage result = sysRolePermService.page(new Page<SysRolePermEntity>(page,pageSize),queryWrapper);
        List<SysRolePermVO> voList = Tool.copyList(result.getRecords(),SysRolePermVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
