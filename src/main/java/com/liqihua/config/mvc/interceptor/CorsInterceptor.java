package com.liqihua.config.mvc.interceptor;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 允许跨域的拦截器
 */
public class CorsInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(CorsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("--- CorsInterceptor request.getMethod():" + request.getMethod());
        /*if(request.getMethod().equalsIgnoreCase("options")){
            response.setStatus(204);
            response.getWriter().print("");
            return false;
        }*/
        String origin = request.getHeader("Origin");
        if(StrUtil.isBlank(origin)){
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Credentials", "false");
        }else{
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }

        response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, Origin, Accept, token");
        response.addHeader("Access-Control-Max-Age", "3600000");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return true;
    }

}
