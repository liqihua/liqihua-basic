package com.liqihua.config.shiro;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class SysSessionManager extends DefaultSessionManager implements WebSessionManager {
    private static final Logger LOG = LoggerFactory.getLogger(SysSessionManager.class);

    private final String HEADER_TOKEN = "token";

    @Override
    protected void onStart(Session session, SessionContext context) {
        // LOG.info("-- onStart");
        if (!WebUtils.isHttp(context)) {
            LOG.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response pair. No session ID cookie will be set.");
        } else {
            HttpServletRequest request = WebUtils.getHttpRequest(context);
            HttpServletResponse response = WebUtils.getHttpResponse(context);
            Serializable sessionId = session.getId();
            this.storeSessionId(sessionId, request, response);
            request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
        }
    }

    /**
     * 获取sessionid
     */
    @Override
    public Serializable getSessionId(SessionKey key) {
        // LOG.info("--- getSessionId");
        Serializable id = super.getSessionId(key);
        if (id == null && WebUtils.isWeb(key)) {
            ServletRequest request = WebUtils.getRequest(key);
            ServletResponse response = WebUtils.getResponse(key);
            id = getSessionId(request, response);
        }
        return id;
    }


    /**
     * 创建session
     * @param session
     * @param context
     * @return
     */
    @Override
    protected Session createExposedSession(Session session, SessionContext context) {
        // LOG.info("--- createExposedSession 1");
        if (!WebUtils.isWeb(context)) {
            return super.createExposedSession(session, context);
        } else {
            ServletRequest request = WebUtils.getRequest(context);
            ServletResponse response = WebUtils.getResponse(context);
            SessionKey key = new WebSessionKey(session.getId(), request, response);
            return new DelegatingSession(this, key);
        }
    }

    @Override
    protected Session createExposedSession(Session session, SessionKey key) {
        // LOG.info("--- createExposedSession 2");
        if (!WebUtils.isWeb(key)) {
            return super.createExposedSession(session, key);
        } else {
            ServletRequest request = WebUtils.getRequest(key);
            ServletResponse response = WebUtils.getResponse(key);
            SessionKey sessionKey = new WebSessionKey(session.getId(), request, response);
            return new DelegatingSession(this, sessionKey);
        }
    }

    @Override
    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        // LOG.info("--- onExpiration");
        super.onExpiration(s, ese, key);
        this.onInvalidation(key);
    }

    @Override
    protected void onInvalidation(Session session, InvalidSessionException ise, SessionKey key) {
        // LOG.info("--- onInvalidation");
        super.onInvalidation(session, ise, key);
        this.onInvalidation(key);
    }

    @Override
    protected void onStop(Session session, SessionKey key) {
        // LOG.info("--- onStop");
        super.onStop(session, key);
        if (WebUtils.isHttp(key)) {
            HttpServletRequest request = WebUtils.getHttpRequest(key);
            HttpServletResponse response = WebUtils.getHttpResponse(key);
            LOG.debug("Session has been stopped (subject logout or explicit stop).  Removing session ID cookie.");
            this.removeSessionIdHeader(request, response);
        } else {
            LOG.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response pair. Session ID cookie will not be removed due to stopped session.");
        }
    }

    @Override
    public boolean isServletContainerSessions() {
        // LOG.info("--- isServletContainerSessions");
        return false;
    }

    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return this.getReferencedSessionId(request, response);
    }


    /**
     * 请求头中获取 sessionId 并把sessionId 放入 response 中
     * @param request
     * @param response
     * @return
     */
    private String getSessionIdHeaderValue(ServletRequest request, ServletResponse response) {
        if (!(request instanceof HttpServletRequest)) {
            LOG.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            /**
             * 在request 中 读取 token 信息  作为 sessionId
             */
            String sessionId = httpRequest.getHeader(this.HEADER_TOKEN);
            /**
             * 每次读取之后 都把当前的 sessionId 放入 response 中
             */
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if (StrUtil.isNotBlank(sessionId)) {
                httpResponse.setHeader(this.HEADER_TOKEN, sessionId);
                LOG.debug("Current session ID is {}", sessionId);
            }
            return sessionId;
        }
    }

    /**
     * 获取sessionid
     * @param request
     * @param response
     * @return
     */
    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
        String id = this.getSessionIdHeaderValue(request, response);

        /**
         * DefaultWebSessionManager 中代码 直接copy过来
         */
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }
        /**
         * 不会把sessionid放在URL后
         */
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, Boolean.FALSE);
        return id;
    }

    /**
     * 移除sessionid 并设置为 deleteMe 标识
     * @param request
     * @param response
     */
    private void removeSessionIdHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.HEADER_TOKEN, "deleteMe");
    }

    /**
     * 把sessionId 放入 response header 中
     * onStart时调用
     * 没有sessionid时 会产生sessionid 并放入 response header中
     */
    private void storeSessionId(Serializable currentId, HttpServletRequest ignored, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        } else {
            String idString = currentId.toString();

            response.setHeader(this.HEADER_TOKEN, idString);

            LOG.info("Set session ID header for session with id {}", idString);

            LOG.trace("Set session ID header for session with id {}", idString);
        }
    }

    private void onInvalidation(SessionKey key) {
        ServletRequest request = WebUtils.getRequest(key);
        if (request != null) {
            request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID);
        }
        if (WebUtils.isHttp(key)) {
            LOG.debug("Referenced session was invalid.  Removing session ID header.");
            this.removeSessionIdHeader(WebUtils.getHttpRequest(key), WebUtils.getHttpResponse(key));
        } else {
            LOG.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response pair. Session ID cookie will not be removed due to invalidated session.");
        }

    }



}
