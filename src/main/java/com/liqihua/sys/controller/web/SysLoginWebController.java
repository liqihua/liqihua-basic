package com.liqihua.sys.controller.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.sys.entity.SysUserEntity;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expireMinute}")
    private Integer expireMinute;
    @Value("${mvc.static.prefix}")
    private String prefix;

    @Resource
    private SysUserService sysUserService;

    /*public static void main(String[] args) {
        String userId = "abc";
        String secret = "123yyuuii";
        Date expireTime = DateUtil.offsetMinute(new Date(),1);
        String token = JWT.create().withAudience("sys").withExpiresAt(expireTime).withClaim("userId",userId).sign(Algorithm.HMAC256(secret));


        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withAudience("sys").build();
        DecodedJWT jwt = verifier.verify(token);
        System.out.println(JSON.toJSONString(jwt));
        Claim claim = jwt.getClaim("userId");
        String str = claim.asString();
        System.out.println(JSON.toJSONString(claim));
        System.out.println(str);
    }*/


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public WebResult login(@RequestParam String username,
                           @RequestParam String password){
        SysUserEntity sysUser = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("username",username));
        if(sysUser == null){
            return buildFailedInfo(ApiConstant.USER_NOT_EXIST);
        }
        if(!password.equals(sysUser.getPassword())){
            return buildFailedInfo(ApiConstant.PASSWORD_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        try{
            subject.login(upToken);
        }catch(ShiroException e){
            LOG.info("--- ShiroException:"+e.getMessage());
            return buildFailedInfo(e.getMessage());
        }
        //LOG.info("--- sessionId : "+subject.getSession().getId());
        LOG.info("--- subject.isAuthenticated():"+subject.isAuthenticated());
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(sysUser,vo);
        if(StrUtil.isNotBlank(vo.getAvatar()) && !vo.getAvatar().contains("http")){
            vo.setAvatar(prefix + vo.getAvatar());
        }

        Date expireTime = DateUtil.offsetMinute(new Date(),expireMinute);
        String token = JWT.create().withExpiresAt(expireTime)
                .withAudience("sys")
                .withClaim("userId",sysUser.getId())
                .sign(Algorithm.HMAC256(jwtSecret));

        Map<String, Object> map = new HashMap<>();
        map.put("user",vo);
        map.put("token",token);
        return buildSuccessInfo(map);
    }


    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public WebResult getInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)) {
            return buildFailedInfo(ApiConstant.NO_LOGIN);
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).withAudience("sys").build();
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim("userId");
            Long userId = claim.asLong();
            SysUserEntity sysUser = sysUserService.getById(userId);
            if(sysUser == null) {
                return buildFailedInfo(ApiConstant.USER_NOT_EXIST);
            }
            SysUserVO vo = new SysUserVO();
            BeanUtils.copyProperties(sysUser,vo);
            if(StrUtil.isNotBlank(vo.getAvatar()) && !vo.getAvatar().contains("http")){
                vo.setAvatar(prefix + vo.getAvatar());
            }
            return buildSuccessInfo(vo);
        }catch (JWTVerificationException e){
            return buildFailedInfo(ApiConstant.NO_LOGIN);
        }
    }




    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public WebResult logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return buildSuccessInfo(null);
    }

    @RequestMapping("/shiroUnauthorizedUrl")
    public WebResult shiroUnauthorizedUrl(){
        return buildFailedInfo("shiroUnauthorizedUrl");
    }

    @RequestMapping("/shiroLoginUrl")
    public WebResult shiroLoginUrl(){
        return buildFailedInfo("shiroLoginUrl");
    }



}
