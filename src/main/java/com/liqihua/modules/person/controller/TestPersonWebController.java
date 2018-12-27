package com.liqihua.modules.person.controller;

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
import com.liqihua.modules.person.entity.TestPersonEntity;
import com.liqihua.modules.person.entity.vo.TestPersonVO;
import com.liqihua.modules.person.service.TestPersonService;
import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * 测试人员
 * @author liqihua
 * @since 2018-12-27
 */
@Api(value="sys-TestPersonWebController",description="测试人员")
@RestController
@RequestMapping("/sys/testPersonWebController")
public class TestPersonWebController extends BaseController {
    @Resource
    private TestPersonService testPersonService;



    @RequiresPermissions("testPerson-list")
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public WebResult page(@ApiParam(value = "页码，1为第一页",required = true) @RequestParam Integer page,
                          @ApiParam(value = "每页数量",required = true) @RequestParam Integer pageSize,
                          @ApiParam(value = "头像") @RequestParam(required=false)  String avatar,
                          @ApiParam(value = "姓名") @RequestParam(required=false)  String name,
                          @ApiParam(value = "密码") @RequestParam(required=false)  String password,
                          @ApiParam(value = "年龄") @RequestParam(required=false)  Integer age,
                          @ApiParam(value = "性别：0女 1男") @RequestParam(required=false)  Boolean gender,
                          @ApiParam(value = "睡觉时间") @RequestParam(required=false) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime sleepTime,
                          @ApiParam(value = "上班时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime workTime,
                          @ApiParam(value = "出生时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                          @ApiParam(value = "个人简介") @RequestParam(required=false)  String intro){
        TestPersonEntity entity = new TestPersonEntity();
        entity.setAvatar(avatar);
        entity.setName(name);
        entity.setPassword(password);
        entity.setAge(age);
        entity.setGender(gender);
        entity.setSleepTime(sleepTime);
        entity.setWorkTime(workTime);
        entity.setBirthday(birthday);
        entity.setIntro(intro);
        QueryWrapper queryWrapper = new QueryWrapper<TestPersonEntity>(entity);
        IPage result = testPersonService.page(new Page<TestPersonEntity>(page,pageSize),queryWrapper);
        List<TestPersonVO> voList = SysBeanUtil.copyList(result.getRecords(),TestPersonVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }


    @RequiresPermissions("testPerson-save")
    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public WebResult save(@ApiParam(value = "id") @RequestParam(required=false) Long id,
                          @ApiParam(value = "头像") @RequestParam(required=false)  String avatar,
                          @ApiParam(value = "姓名") @RequestParam(required=false)  String name,
                          @ApiParam(value = "密码") @RequestParam(required=false)  String password,
                          @ApiParam(value = "年龄") @RequestParam(required=false)  Integer age,
                          @ApiParam(value = "性别：0女 1男") @RequestParam(required=false)  Boolean gender,
                          @ApiParam(value = "睡觉时间") @RequestParam(required=false) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime sleepTime,
                          @ApiParam(value = "上班时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime workTime,
                          @ApiParam(value = "出生时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                          @ApiParam(value = "个人简介") @RequestParam(required=false)  String intro
                          ){
        TestPersonEntity entity = null;
        if(id != null){
            entity = testPersonService.getById(id);
        }else{
            entity = new TestPersonEntity();
        }
        entity.setAvatar(avatar);
        entity.setName(name);
        entity.setPassword(password);
        entity.setAge(age);
        entity.setGender(gender);
        entity.setSleepTime(sleepTime);
        entity.setWorkTime(workTime);
        entity.setBirthday(birthday);
        entity.setIntro(intro);
        testPersonService.saveOrUpdate(entity);
        TestPersonVO vo = new TestPersonVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }


    @RequiresPermissions("testPerson-delete")
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public WebResult delete(@RequestParam Long id){
        boolean delete = testPersonService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @RequiresAuthentication
    @ApiOperation(value = "获取详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public WebResult get(@RequestParam Long id){
        TestPersonEntity entity = testPersonService.getById(id);
        TestPersonVO vo = null;
        if(entity != null){
            vo = new TestPersonVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



}
