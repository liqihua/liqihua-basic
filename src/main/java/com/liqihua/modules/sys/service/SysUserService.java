package com.liqihua.modules.sys.service;

import com.liqihua.modules.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  用户 服务类
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 刷新当前用户的角色和权限
     */
    public void refreshRealm();


    public void refreshUserMenu();

}
