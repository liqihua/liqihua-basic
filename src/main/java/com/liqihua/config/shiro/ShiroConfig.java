package com.liqihua.config.shiro;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 注册realm
     * @return
     */
	@Bean
	public SysRealm sysRealm(){
		return new SysRealm();
	}

	@Bean
	public SessionManager sessionManager() {
		SysSessionManager sessionManager = new SysSessionManager();
		return sessionManager;
	}


    /**
     * 注册shiro的DefaultWebSecurityManager为SecurityManager
     * @return
     */
	@Bean
    public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setSessionManager(sessionManager());
		securityManager.setRealm(sysRealm());
		return securityManager;
    }


    /**
     * 拦截配置
     * @return
     */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager());
		bean.setUnauthorizedUrl("/shiroUnauthorizedUrl");
		bean.setLoginUrl("/shiroLoginUrl");
		/**
		 * 自己实现AccessControlFilter，因为默认的authc过滤器会重定向权限不足的请求（302），而不是抛出ShiroException
		 * AccessControlFilter的onAccessDenied()返回true后，会抛出ShiroException
		 * 在com.liqihua.config.advice.SysControllerAdvice中捕获ShiroException异常并响应json格式的错误
		 */
        Map<String, Filter> authcFilters = new HashMap<>();
        authcFilters.put("sysAuthc", new SysAuthcFilter());
		bean.setFilters(authcFilters);

		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		filterMap.put("/sys/sysLoginWebController/**", "anon");
		filterMap.put("/sys/**", "sysAuthc");
		bean.setFilterChainDefinitionMap(filterMap);
		return bean;
	}


    /**
     * 启用shiro注解
     * @param securityManager
     * @return
     */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}


	//创建代理
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	//shiro回收机制
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}

}
