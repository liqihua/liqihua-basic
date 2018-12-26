package ${package.Controller};

import com.liqihua.common.utils.SysBeanUtil;
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
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import ${package.Entity}.${entity};
import ${package.Entity}.vo.${entity?replace('Entity','VO')};
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(value="api-${table.controllerName}",description="${entity?replace('Entity','')}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/api/${entity?uncap_first?replace('Entity','ApiController')}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};



    @RequiresPermissions("${entity?replace('Entity','')?uncap_first}-list")
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = ${entity?replace('Entity','VO')}.class)})
    public WebResult page(@ApiParam(value = "页码，1为第一页",required = true) @RequestParam Integer page,
                          @ApiParam(value = "每页数量",required = true) @RequestParam Integer pageSize,
                          <#list table.fields as field>
                          <#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
                          @ApiParam(value = "${field.comment}") @RequestParam(required=false) <#if field.propertyType == 'LocalDateTime' >@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")</#if><#if field.propertyType == 'LocalDate' >@DateTimeFormat(pattern = "yyyy-MM-dd")</#if><#if field.propertyType == 'LocalTime' >@DateTimeFormat(pattern = "HH:mm:ss")</#if> ${field.propertyType} ${field.propertyName}<#if (field_index != table.fields?size-3)>,<#else>){</#if>
                          </#if>
                          </#list>
        ${entity} entity = new ${entity}();
    <#list table.fields as field>
        <#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
        entity.set${field.capitalName}(${field.propertyName});
        </#if>
    </#list>
        QueryWrapper queryWrapper = new QueryWrapper<${entity}>(entity);
        IPage result = ${table.serviceName?uncap_first}.page(new Page<${entity}>(page,pageSize),queryWrapper);
        List<${entity?replace('Entity','VO')}> voList = SysBeanUtil.copyList(result.getRecords(),${entity?replace('Entity','VO')}.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }


    @RequiresPermissions("${entity?replace('Entity','')?uncap_first}-save")
    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = ${entity?replace('Entity','VO')}.class)})
    public WebResult save(@ApiParam(value = "id") @RequestParam(required=false) Long id,
                          <#list table.fields as field><#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>@ApiParam(value = "${field.comment}") @RequestParam(required=false) <#if field.propertyType == 'LocalDateTime' >@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")</#if><#if field.propertyType == 'LocalDate' >@DateTimeFormat(pattern = "yyyy-MM-dd")</#if><#if field.propertyType == 'LocalTime' >@DateTimeFormat(pattern = "HH:mm:ss")</#if> ${field.propertyType} ${field.propertyName}<#if (field_index != table.fields?size-3)>,</#if>
                          </#if></#list>){
        ${entity} entity = null;
        if(id != null){
            entity = ${table.serviceName?uncap_first}.getById(id);
        }else{
            entity = new ${entity}();
        }
        <#list table.fields as field>
        <#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
        entity.set${field.capitalName}(${field.propertyName});
        </#if>
        </#list>
        ${table.serviceName?uncap_first}.saveOrUpdate(entity);
        ${entity?replace('Entity','VO')} vo = new ${entity?replace('Entity','VO')}();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }


    @RequiresPermissions("${entity?replace('Entity','')?uncap_first}-delete")
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public WebResult delete(@RequestParam Long id){
        boolean delete = ${table.serviceName?uncap_first}.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequiresAuthentication
    @ApiOperation(value = "获取详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = ${entity?replace('Entity','VO')}.class)})
    public WebResult get(@RequestParam Long id){
        ${entity} entity = ${table.serviceName?uncap_first}.getById(id);
        ${entity?replace('Entity','VO')} vo = null;
        if(entity != null){
            vo = new ${entity?replace('Entity','VO')}();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



}
</#if>
