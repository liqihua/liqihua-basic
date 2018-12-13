package com.liqihua.config.shiro;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.sys.controller.web.SysLoginWebController;
import com.liqihua.sys.entity.*;
import com.liqihua.sys.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
		LOG.info("--- SysRealm doGetAuthorizationInfo() ");
		SysUserEntity sysUser = (SysUserEntity)principals.getPrimaryPrincipal();
		if(sysUser == null){
			return null;
		}
		Set<String> roles = new HashSet<>();
		Set<String> perms = new HashSet<>();
		List<SysRoleUserEntity> ruList = sysRoleUserService.list(new QueryWrapper<SysRoleUserEntity>().eq("user_id",sysUser.getId()));
		if(ruList != null && ruList.size() > 0) {
			List<Long> roleIdList = ruList.stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());
			List<SysRoleEntity> roleList = sysRoleService.list(new QueryWrapper<SysRoleEntity>().in("id",roleIdList));
			if(roleList != null && roleList.size() > 0){
				roles = roleList.stream().map(SysRoleEntity::getName).collect(Collectors.toSet());
			}

			List<SysRolePermEntity> rpList = sysRolePermService.list(new QueryWrapper<SysRolePermEntity>().in("role_id",roleIdList));
			if(rpList != null && rpList.size() > 0) {
				List<Long> permIdList = rpList.stream().map(SysRolePermEntity::getPermId).collect(Collectors.toList());
				if(permIdList != null && permIdList.size() > 0){
					List<SysPermEntity> permList = sysPermService.list(new QueryWrapper<SysPermEntity>().in("id",permIdList));
					if(permList != null && permList.size() > 0){
						perms = permList.stream().map(SysPermEntity::getSymbol).collect(Collectors.toSet());
					}
				}
			}
		}

		LOG.info("roles:\n"+ JSON.toJSONString(roles));
		LOG.info("perms:\n"+ JSON.toJSONString(perms));

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LOG.info("--- SysRealm doGetAuthenticationInfo() ");
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
