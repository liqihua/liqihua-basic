package com.liqihua.crud.controller.api;

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
import com.liqihua.crud.entity.TestPersonEntity;
import com.liqihua.crud.entity.vo.TestPersonVO;
import com.liqihua.crud.service.TestPersonService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-10-15
 */
@Api(value="api-TestPersonApiController",description="TestPerson")
@RestController
@RequestMapping("/api/testPersonApiController")
public class TestPersonApiController extends BaseController {
    @Resource
    private TestPersonService testPersonService;






    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public WebResult save(@ApiParam(value = "name",required = true) @RequestParam(value="name",required = true)  String name,
                          @ApiParam(value = "password",required = true) @RequestParam(value="password",required = true)  String password,
                          @ApiParam(value = "age",required = true) @RequestParam(value="age",required = true)  Integer age,
                          @ApiParam(value = "gender",required = true) @RequestParam(value="gender",required = true)  Boolean gender,
                          @ApiParam(value = "avatar",required = true) @RequestParam(value="avatar",required = true)  String avatar,
                          @ApiParam(value = "birthday",required = true) @RequestParam(value="birthday",required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                          @ApiParam(value = "workTime",required = true) @RequestParam(value="workTime",required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime workTime,
                          @ApiParam(value = "intro",required = true) @RequestParam(value="intro",required = true)  String intro
                          ){
        TestPersonEntity entity = new TestPersonEntity();
        entity.setName(name);
        entity.setPassword(password);
        entity.setAge(age);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setBirthday(birthday);
        entity.setWorkTime(workTime);
        entity.setIntro(intro);
        testPersonService.saveOrUpdate(entity);
        TestPersonVO vo = new TestPersonVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }



    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public WebResult delete(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        boolean delete = testPersonService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @ApiOperation(value = "获取详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public WebResult get(@ApiParam(value = "id",required = true) @RequestParam(value="id",required = true) Long id){
        TestPersonEntity entity = testPersonService.getById(id);
        TestPersonVO vo = null;
        if(entity != null){
            vo = new TestPersonVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public WebResult page(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                          @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize,
                          @ApiParam(value = "name",required = false) @RequestParam(value="name",required = false)  String name,
                          @ApiParam(value = "password",required = false) @RequestParam(value="password",required = false)  String password,
                          @ApiParam(value = "age",required = false) @RequestParam(value="age",required = false)  Integer age,
                          @ApiParam(value = "gender",required = false) @RequestParam(value="gender",required = false)  Boolean gender,
                          @ApiParam(value = "avatar",required = false) @RequestParam(value="avatar",required = false)  String avatar,
                          @ApiParam(value = "birthday",required = false) @RequestParam(value="birthday",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                          @ApiParam(value = "workTime",required = false) @RequestParam(value="workTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime workTime,
                          @ApiParam(value = "intro",required = false) @RequestParam(value="intro",required = false)  String intro){
        TestPersonEntity entity = new TestPersonEntity();
        entity.setName(name);
        entity.setPassword(password);
        entity.setAge(age);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setBirthday(birthday);
        entity.setWorkTime(workTime);
        entity.setIntro(intro);
        QueryWrapper queryWrapper = new QueryWrapper<TestPersonEntity>(entity);
        IPage result = testPersonService.page(new Page<TestPersonEntity>(page,pageSize),queryWrapper);
        List<TestPersonVO> voList = Tool.copyList(result.getRecords(),TestPersonVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }




}
