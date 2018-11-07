package ${package.Controller};

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






    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = ${entity?replace('Entity','VO')}.class)})
    public WebResult save(<#list table.fields as field><#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>@ApiParam(value = "${field.comment}") @RequestParam(value="${field.propertyName}",required = true) <#if field.propertyType == 'LocalDateTime' >@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")</#if><#if field.propertyType == 'LocalDate' >@DateTimeFormat(pattern = "yyyy-MM-dd")</#if><#if field.propertyType == 'LocalTime' >@DateTimeFormat(pattern = "HH:mm:ss")</#if> ${field.propertyType} ${field.propertyName}<#if (field_index != table.fields?size-3)>,</#if>
                          </#if></#list>){
        ${entity} entity = new ${entity}();
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



    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = ${table.serviceName?uncap_first}.removeById(id);
        return buildSuccessInfo(delete);
    }


    @ApiOperation(value = "获取详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = ${entity?replace('Entity','VO')}.class)})
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        ${entity} entity = ${table.serviceName?uncap_first}.getById(id);
        ${entity?replace('Entity','VO')} vo = null;
        if(entity != null){
            vo = new ${entity?replace('Entity','VO')}();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = ${entity?replace('Entity','VO')}.class)})
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          <#list table.fields as field>
                          <#if field.propertyName != 'id' && field.propertyName != 'createDate' && field.propertyName != 'updateDate'>
                          @ApiParam(value = "${field.propertyName}",required = false) @RequestParam(value="${field.propertyName}",required = false) <#if field.propertyType == 'LocalDateTime' >@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")</#if><#if field.propertyType == 'LocalDate' >@DateTimeFormat(pattern = "yyyy-MM-dd")</#if><#if field.propertyType == 'LocalTime' >@DateTimeFormat(pattern = "HH:mm:ss")</#if> ${field.propertyType} ${field.propertyName}<#if (field_index != table.fields?size-3)>,<#else>){</#if>
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
        List<${entity?replace('Entity','VO')}> voList = Tool.copyList(result.getRecords(),${entity?replace('Entity','VO')}.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
</#if>
