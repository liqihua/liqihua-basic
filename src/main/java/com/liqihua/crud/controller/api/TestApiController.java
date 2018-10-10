package com.liqihua.crud.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.crud.entity.TestPersonEntity;
import com.liqihua.crud.service.TestPersonService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author liqihua
 * @since 2018/10/10
 */
@Api(value="testApiController",description="调试")
@RestController
@RequestMapping("/api/testApiController")
public class TestApiController extends BaseController{
    @Resource
    private TestPersonService testPersonService;


    @ApiOperation(value = "test1")
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    public WebResult test1(@ApiParam(value = "aa",required = true) @RequestParam(value="aa",required=true) String aa){
        return buildSuccessInfo(testPersonService.list(null));
    }




    @ApiOperation(value = "testInsert")
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    @RequestMapping(value = "/testInsert", method = RequestMethod.POST)
    public WebResult testInsert(){
        int num =  (int)(Math.random()*1000);
        TestPersonEntity entity = new TestPersonEntity();
        entity.setAge(num);
        entity.setAvatar("avatar"+num);
        entity.setBirthday(LocalDateTime.now());
        entity.setName("name"+num);
        entity.setPassword("password"+num);
        return buildSuccessInfo(testPersonService.save(entity));
    }



    @ApiOperation(value = "testPage")
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    @RequestMapping(value = "/testPage", method = RequestMethod.POST)
    public WebResult testPage(){
        IPage page = testPersonService.page(new Page<TestPersonEntity>(1,5),null);
        return buildSuccessInfo(page);
    }


}
