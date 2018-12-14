package com.liqihua.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SysFileUtil;
import com.liqihua.sys.entity.SysMenuEntity;
import com.liqihua.sys.entity.SysPermMenuEntity;
import com.liqihua.sys.service.SysMenuService;
import com.liqihua.sys.service.SysPermMenuService;
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
import java.util.List;

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
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysPermMenuService sysPermMenuService;


    @ApiOperation(value = "test1")
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult test1(@ApiParam(value = "aa",required = false) @RequestParam(value="aa",required = false) String aa){
        QueryWrapper qw = new QueryWrapper<SysPermMenuEntity>().select("menu_id").groupBy("menu_id");
        return buildSuccessInfo(sysPermMenuService.list(qw));
    }




    @ApiOperation(value = "test2")
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult test2(){
        QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",3);
        queryWrapper.eq("title","aabb");
        queryWrapper.select("pid");
        List<SysMenuEntity> list = sysMenuService.list(queryWrapper);
        return buildSuccessInfo(list);
    }


    @ApiOperation(value = "test3")
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult test3(@RequestParam(required = true) String aa,
                           @RequestParam(required = true) Integer bb,
                           /*@RequestParam(required = false) LocalDateTime cc,
                           @RequestParam(required = false) LocalDate dd,
                           @RequestParam(required = false) LocalTime ee,*/
                           @RequestParam(required = false) Boolean ff){
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
