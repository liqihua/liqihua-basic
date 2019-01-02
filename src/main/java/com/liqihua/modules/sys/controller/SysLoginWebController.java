package com.liqihua.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.constant.Constants;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.modules.sys.entity.SysMenuEntity;
import com.liqihua.modules.sys.entity.SysRoleMenuEntity;
import com.liqihua.modules.sys.entity.SysRoleUserEntity;
import com.liqihua.modules.sys.entity.SysUserEntity;
import com.liqihua.modules.sys.entity.vo.SysMenuVO;
import com.liqihua.modules.sys.entity.vo.SysUserVO;
import com.liqihua.modules.sys.service.SysMenuService;
import com.liqihua.modules.sys.service.SysRoleMenuService;
import com.liqihua.modules.sys.service.SysRoleUserService;
import com.liqihua.modules.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  用户 前端控制器
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/sys/sysLoginWebController")
public class SysLoginWebController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysLoginWebController.class);

    @Value("${mvc.static.prefix}")
    private String prefix;

    @Resource
    private SysUserService sysUserService;


    /**
     * 登录
     * @author liqihua
     * @since 2019/1/2
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public WebResult login(HttpServletRequest request,
                           @RequestParam String username,
                           @RequestParam String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        try{
            subject.login(upToken);
        }catch(ShiroException e){
            LOG.info("--- ShiroException:"+e.getMessage());
            return buildFailedInfo(e.getMessage());
        }
        LOG.info("--- login sessionId-subject:"+ subject.getSession().getId());
        LOG.info("--- login sessionId-request:"+request.getSession().getId());
        SysUserEntity sysUser = (SysUserEntity)subject.getPrincipal();
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(sysUser,vo);
        if(StrUtil.isNotBlank(vo.getAvatar()) && !vo.getAvatar().contains("http")){
            vo.setAvatar(prefix + vo.getAvatar());
        }

        /**
         * 把用户信息放session里
         */
        subject.getSession().setAttribute(Constants.KEY_SESSION_SYS_USER_INFO,vo);
        /**
         * 刷新用户权限和角色
         */
        sysUserService.refreshRealm();
        /**
         * 刷新用户菜单列表
         */
        sysUserService.refreshUserMenu();

        Map<String, Object> map = new HashMap<>();
        map.put("token",subject.getSession().getId());
        map.put("user",subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_INFO));
        map.put("menuList",subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_MENU));
        return buildSuccessInfo(map);
    }


    /**
     * 获取用户信息
     * @author liqihua
     * @since 2019/1/2
     */
    @RequiresAuthentication
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public WebResult getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        return buildSuccessInfo(subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_INFO));
    }


    /**
     * 获取用户菜单
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/getUserMenu", method = RequestMethod.GET)
    public WebResult getUserMenu(){
        Subject subject = SecurityUtils.getSubject();
        return buildSuccessInfo(subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_MENU));
    }

    /**
     * 更新当前用户信息
     * @author liqihua
     * @since 2019/1/2
     */
    @RequiresAuthentication
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public WebResult updateUser(String password,
                          String nickname,
                          String realName,
                          Boolean gender,
                          String avatar,
                          String mobile){
        Subject subject = SecurityUtils.getSubject();
        SysUserEntity entity = (SysUserEntity)subject.getPrincipal();
        if(entity == null){
            return buildFailedInfo(ApiConstant.NO_LOGIN);
        }
        if(StrUtil.isNotBlank(avatar)){
            avatar = avatar.replace(prefix,"");
        }
        if(StrUtil.isNotBlank(password)){
            entity.setPassword(password);
        }
        entity.setNickname(nickname);
        entity.setRealName(realName);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setMobile(mobile);
        sysUserService.saveOrUpdate(entity);
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(entity,vo);
        if(StrUtil.isNotBlank(vo.getAvatar()) && vo.getAvatar().startsWith("/")){
            vo.setAvatar(prefix + vo.getAvatar());
        }
        /**
         * 更新session里的用户信息
         */
        subject.getSession().setAttribute(Constants.KEY_SESSION_SYS_USER_INFO,vo);
        return buildSuccessInfo(vo);
    }


    /**
     * 退出登录
     * @author liqihua
     * @since 2019/1/2
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public WebResult logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return buildSuccessInfo(null);
    }


}
