package com.liqihua.config.shiro;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自己实现AccessControlFilter，因为默认的authc过滤器会重定向权限不足的请求（302），而不是抛出ShiroException
 * AccessControlFilter的onAccessDenied()返回true后，会抛出ShiroException
 * 在com.liqihua.config.advice.SysControllerAdvice中捕获ShiroException异常并响应json格式的错误
 * @author liqihua
 * @since 2018/12/13
 */

public class SysAuthcFilter extends AccessControlFilter{
    private static final Logger LOG = LoggerFactory.getLogger(SysAuthcFilter.class);

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

        /*HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;*/

        /*Subject subject = SecurityUtils.getSubject();
        LOG.info("--- sessionId:"+ subject.getSession().getId());
        LOG.info("cookies:"+ JSON.toJSONString(request.getCookies()));*/

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

}
