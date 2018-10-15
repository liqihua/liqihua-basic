package com.liqihua.crud.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.crud.entity.TestPersonEntity;
import com.liqihua.crud.service.TestPersonService;
import io.swagger.annotations.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author liqihua
 * @since 2018/10/11
 */


@Api(value="api-TestApiController",description="测试接口（仅供后台调试使用）")
@RequestMapping("/api/testApiController")
@RestController
public class TestApiController extends BaseController{
    @Resource
    private TestPersonService testPersonService;





    @ApiOperation(value = "testList")
    @RequestMapping(value = "/testList", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonEntity.class)})
    public WebResult testList(){
        TestPersonEntity _q = new TestPersonEntity();
        _q.setName("333");
        _q.setGender(null);
        _q.setIntro("");
        QueryWrapper wrapper = new QueryWrapper(_q);
        return buildSuccessInfo(testPersonService.list(wrapper));
    }



    @ApiOperation(value = "testPage")
    @RequestMapping(value = "/testPage", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonEntity.class)})
    public WebResult testPage(@ApiParam(value = "page",required = true) @RequestParam(value="page",required=true) Integer page,
                              @ApiParam(value = "pageSize",required = true) @RequestParam(value="pageSize",required=true) Integer pageSize){
        IPage<TestPersonEntity> p = testPersonService.page(new Page<TestPersonEntity>(page,pageSize),null);
        return buildSuccessInfo(p);
    }



    @ApiOperation(value = "makeData")
    @RequestMapping(value = "/makeData", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonEntity.class)})
    public WebResult makeData(){
        int num = (int)(Math.random()*1000);
        TestPersonEntity entity = new TestPersonEntity();
        entity.setName("name_"+num);
        entity.setPassword("pwd_"+num);
        entity.setAvatar("avatar_"+num);
        entity.setAge(num);
        entity.setGender((num % 2 == 0) ? true : false);
        entity.setBirthday(LocalDate.now());
        entity.setWorkTime(LocalTime.now());
        entity.setIntro("intro_"+num);
        testPersonService.save(entity);
        return buildSuccessInfo(entity);
    }


    @ApiOperation(value = "testInsert")
    @RequestMapping(value = "/testInsert", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonEntity.class)})
    public WebResult testInsert(@ApiParam(value = "birthday",required = true) @RequestParam(value="birthday",required=true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                              @ApiParam(value = "workTime",required = true) @RequestParam(value="workTime",required=true) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime workTime){
        int num = (int)(Math.random()*1000);
        TestPersonEntity entity = new TestPersonEntity();
        entity.setName("name_"+num);
        entity.setPassword("pwd_"+num);
        entity.setAvatar("avatar_"+num);
        entity.setAge(num);
        entity.setGender((num % 2 == 0) ? true : false);
        entity.setBirthday(birthday);
        entity.setWorkTime(workTime);
        entity.setIntro("intro_"+num);
        testPersonService.save(entity);
        return buildSuccessInfo(entity);
    }


    /**
     * 接收日期参数类型LocalDateTime、LocalDate、LocalTime需要加注解@DateTimeFormat
     * 否则按照传统方法，先用String接收，再转日期
     * @param birthday
     * @param workTime
     * @param sleepTime
     * @return
     */
    @ApiOperation(value = "testLocalDateTime")
    @RequestMapping(value = "/testLocalDateTime", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testLocalDateTime(@ApiParam(value = "birthday",required = true) @RequestParam(value="birthday",required=true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime birthday,
                                       @ApiParam(value = "workTime",required = true) @RequestParam(value="workTime",required=true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate workTime,
                                       @ApiParam(value = "sleepTime",required = true) @RequestParam(value="sleepTime",required=true) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime sleepTime){
        System.out.println(birthday.toString());
        System.out.println(workTime.toString());
        System.out.println(sleepTime.toString());
        return buildSuccessInfo(birthday);
    }




}
