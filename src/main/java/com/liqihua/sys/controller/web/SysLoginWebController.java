package com.liqihua.sys.controller.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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

    /*public static void main(String[] args) {
        String userId = "abc";
        String secret = "123yyuuii";
        Date expireTime = DateUtil.offsetMinute(new Date(),1);
        String token = JWT.create().withExpiresAt(expireTime).withClaim("userId",userId).sign(Algorithm.HMAC256(secret));


        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        System.out.println(JSON.toJSONString(jwt));
        //jwt = JWT.decode(token);
        Claim claim = jwt.getClaim("userId");
        String str = claim.asString();
        System.out.println(JSON.toJSONString(claim));
        System.out.println(str);
    }*/


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
