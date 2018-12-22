package com.liqihua.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.common.constant.Constants;
import com.liqihua.modules.sys.entity.SysUserEntity;
import com.liqihua.modules.sys.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Set;

public class SysRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(SysRealm.class);

	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleUserService sysRoleUserService;
	@Resource
	private SysRolePermService sysRolePermService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysPermService sysPermService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// LOG.info("--- SysRealm doGetAuthorizationInfo() ");
		SysUserEntity sysUser = (SysUserEntity)principals.getPrimaryPrincipal();
		if(sysUser == null){
			return null;
		}
		Subject subject = SecurityUtils.getSubject();
		Set<String> roles = (Set<String>) subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_ROLES);
		Set<String> perms = (Set<String>) subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_PERMS);

		// LOG.info("roles:\n"+ JSON.toJSONString(roles));
		// LOG.info("perms:\n"+ JSON.toJSONString(perms));

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// LOG.info("--- SysRealm doGetAuthenticationInfo() ");
		String username = token.getPrincipal().toString();
		String password = new String((char[])token.getCredentials());
		SysUserEntity sysUser = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("username",username).eq("password",password));
		if(sysUser == null){
			throw new UnknownAccountException("用户名或密码错误");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, password, getName());
		return info;
	}

}
