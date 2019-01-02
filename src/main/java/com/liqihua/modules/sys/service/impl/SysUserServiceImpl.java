package com.liqihua.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.common.constant.Constants;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.modules.sys.dao.*;
import com.liqihua.modules.sys.entity.*;
import com.liqihua.modules.sys.entity.vo.SysMenuVO;
import com.liqihua.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  用户 服务实现类
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Resource
    SysRoleDao sysRoleDao;
    @Resource
    SysRoleUserDao sysRoleUserDao;
    @Resource
    SysRolePermDao sysRolePermDao;
    @Resource
    SysRoleMenuDao sysRoleMenuDao;
    @Resource
    SysPermDao sysPermDao;
    @Resource
    SysMenuDao sysMenuDao;



    @Override
    public void refreshRealm() {
        Subject subject = SecurityUtils.getSubject();
        SysUserEntity sysUser = (SysUserEntity)subject.getPrincipal();
        if(sysUser != null){
            Set<String> roles = null;
            Set<String> perms = null;
            List<SysRoleUserEntity> ruList = sysRoleUserDao.selectList(new QueryWrapper<SysRoleUserEntity>().eq("user_id",sysUser.getId()));
            if(ruList != null && ruList.size() > 0) {
                List<Long> roleIdList = ruList.stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());
                List<SysRoleEntity> roleList = sysRoleDao.selectList(new QueryWrapper<SysRoleEntity>().in("id",roleIdList));
                if(roleList != null && roleList.size() > 0){
                    roles = roleList.stream().map(SysRoleEntity::getName).collect(Collectors.toSet());
                }
                List<SysRolePermEntity> rpList = sysRolePermDao.selectList(new QueryWrapper<SysRolePermEntity>().in("role_id",roleIdList));
                if(rpList != null && rpList.size() > 0) {
                    List<Long> permIdList = rpList.stream().map(SysRolePermEntity::getPermId).collect(Collectors.toList());
                    if(permIdList != null && permIdList.size() > 0){
                        List<SysPermEntity> permList = sysPermDao.selectList(new QueryWrapper<SysPermEntity>().in("id",permIdList));
                        if(permList != null && permList.size() > 0){
                            perms = permList.stream().map(SysPermEntity::getSymbol).collect(Collectors.toSet());
                        }
                    }
                }
            }

            if(roles != null){
                subject.getSession().setAttribute(Constants.KEY_SESSION_SYS_USER_ROLES,roles);
            }
            if(perms != null){
                subject.getSession().setAttribute(Constants.KEY_SESSION_SYS_USER_PERMS,perms);
            }
        }
    }

    @Override
    public void refreshUserMenu() {
        Subject subject = SecurityUtils.getSubject();
        SysUserEntity sysUser = (SysUserEntity)subject.getPrincipal();
        List<SysRoleUserEntity> ruList = sysRoleUserDao.selectList(new QueryWrapper<SysRoleUserEntity>().eq("user_id",sysUser.getId()));
        if(ruList != null && ruList.size() > 0){
            List<Long> roleIdList = ruList.stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());
            List<SysRoleMenuEntity> rmList = sysRoleMenuDao.selectList(new QueryWrapper<SysRoleMenuEntity>().in("role_id",roleIdList));
            if(rmList != null && rmList.size() > 0){
                List<Long> menuIdList = rmList.stream().map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList());
                List<SysMenuEntity> menuList = sysMenuDao.selectList(new QueryWrapper<SysMenuEntity>().in("id",menuIdList).eq("hide",false));
                List<SysMenuVO> menuVOList = SysBeanUtil.copyList(menuList,SysMenuVO.class);
                /**
                 * 把用户菜单放session里
                 */
                subject.getSession().setAttribute(Constants.KEY_SESSION_SYS_USER_MENU,menuVOList);
            }
        }
    }


}
