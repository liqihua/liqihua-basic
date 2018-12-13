package com.liqihua.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.liqihua.sys.controller.web.SysLoginWebController;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author liqihua
 * @since 2018/12/13
 */
public class SysSessionManager extends DefaultWebSessionManager {
    private static final Logger LOG = LoggerFactory.getLogger(SysSessionManager.class);



    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = request.getHeader("token");
        LOG.info("--- token : " + token);
        if(StrUtil.isNotBlank(token)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,"token");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            return token;
        } else {
            return super.getSessionId(servletRequest, servletResponse);
        }
    }




}
