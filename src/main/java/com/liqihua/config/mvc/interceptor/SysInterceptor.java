package com.liqihua.config.mvc.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.sys.controller.web.SysLoginWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liqihua
 * @since 2018/12/11
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SysLoginWebController.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        LOG.info("token:"+token);
        if(StrUtil.isBlank(token)) {
            noLogin(response);
            return false;
        }
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).withAudience("sys").build();
        try {
            verifier.verify(token);
        }catch (JWTVerificationException e) {
            noLogin(response);
            return false;
        }
        return true;
    }


    private void noLogin(HttpServletResponse response){
        try {
            WebResult result = new WebResult(ApiConstant.NO_LOGIN,ApiConstant.getMessage(ApiConstant.NO_LOGIN));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
