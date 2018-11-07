package com.liqihua.sys.controller.web;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.utils.Tool;
import com.liqihua.crud.controller.api.TestApiController;
import com.liqihua.sys.entity.SysUserEntity;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.SysUserService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  用户 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysLoginWebController")
public class SysLoginWebController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysLoginWebController.class);

    @Resource
    private SysUserService sysUserService;




    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public WebResult login(@RequestParam(required = true) String username,
                           @RequestParam(required = true) String password){
        LOG.info("username:"+username+",password:"+password);
        return buildSuccessInfo(RandomUtil.randomString(32));
    }


    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public WebResult getInfo(@RequestParam(required = true) String token){
        LOG.info("--- getInfo token:"+token);
        SysUserVO vo = new SysUserVO();
        vo.setUsername("liqihua");
        vo.setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541589629203&di=b4d4bbc8c7f642e1ab277515681e1bb3&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3841743209%2C952064471%26fm%3D214%26gp%3D0.jpg");
        vo.setNickname("周杰伦");
        return buildSuccessInfo(vo);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public WebResult logout(){
        return buildSuccessInfo(null);
    }




}
