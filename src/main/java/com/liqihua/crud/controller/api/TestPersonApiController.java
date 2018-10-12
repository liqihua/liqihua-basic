package com.liqihua.crud.controller.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.liqihua.common.basic.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-10-12
 */
@Api(value="api-TestPersonApiController",description="TestPerson")
@RestController
@RequestMapping("/api/testPersonApiController")
public class TestPersonApiController extends BaseController {

}
