package com.liqihua.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.utils.SpringContextHolder;
import com.liqihua.sys.entity.SysUserEntity;
import com.liqihua.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liqihua
 * @since 2018/12/13
 */

public class SysAuthcFilter extends AccessControlFilter{
    private static final Logger LOG = LoggerFactory.getLogger(SysAuthcFilter.class);


    private static Environment environment;
    private static String jwtSecret;
    private static SysUserService sysUserService;

    /**
     * 表示是否允许访问 ，如果允许访问返回true，否则false；
     * @param servletRequest
     * @param servletResponse
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object object) throws Exception {
        // LOG.info("--- SysAuthcFilter isAccessAllowed()");
        if(StrUtil.isBlank(jwtSecret)) {
            if(environment == null){
                environment = SpringContextHolder.getBean(Environment.class);
            }
            jwtSecret = environment.getProperty("jwt.secret");
        }
        if(sysUserService == null){
            sysUserService = SpringContextHolder.getBean(SysUserService.class);
        }

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)) {
            noLogin(response);
            return false;
        }
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).withAudience("sys").build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Long userId = jwt.getClaim("userId").asLong();
            SysUserEntity sysUser = sysUserService.getById(userId);
            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(sysUser, sysUser.getUsername());
            SecurityManager securityManager = SecurityUtils.getSecurityManager();
            WebDelegatingSubject webDelegatingSubject = new WebDelegatingSubject(principalCollection, true, request.getRemoteHost(), null, false, request,response, securityManager);
            ThreadContext.bind(webDelegatingSubject);
        }catch (JWTVerificationException e) {
            LOG.error("JWTVerificationException : " + e.getMessage());
            noLogin(response);
            return false;
        }
        return true;
    }

    /**
     * 返回 false 表示已经处理，例如页面跳转啥的，表示不在走以下的拦截器了（如果还有配置的话）
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // LOG.info("--- SysAuthcFilter onAccessDenied()");
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
