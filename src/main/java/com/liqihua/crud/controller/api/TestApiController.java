package com.liqihua.crud.controller.api;

import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysFileUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    private static final Logger LOG = LoggerFactory.getLogger(TestApiController.class);

    @Resource
    private Environment environment;

    @ApiOperation(value = "test1")
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult test1(@ApiParam(value = "aa",required = false) @RequestParam(value="aa",required = false) String aa){
        return buildSuccessInfo(aa);
    }


    @ApiOperation(value = "test3")
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult test3(@RequestParam(value="aa",required = true) String aa,
                           @RequestParam(value="bb",required = true) Integer bb,
                           @RequestParam(value="cc",required = false) Integer cc){
        LOG.info(aa,bb);
        return buildSuccessInfo(aa);
    }


    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation(value = "uploadFile")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult uploadFile(MultipartFile file){
        String path = SysFileUtil.uploadFile(file,null);
        String prefix = environment.getProperty("mvc.static.prefix");
        return buildSuccessInfo(prefix + path);
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
