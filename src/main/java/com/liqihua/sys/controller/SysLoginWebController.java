package com.liqihua.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.constant.Constants;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.sys.entity.*;
import com.liqihua.sys.entity.vo.SysMenuVO;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import java.util.*;
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
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysMenuService sysMenuService;


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


        Map<String, Object> map = new HashMap<>();
        map.put("user",vo);
        map.put("token",subject.getSession().getId());

        List<SysRoleUserEntity> ruList = sysRoleUserService.list(new QueryWrapper<SysRoleUserEntity>().eq("user_id",sysUser.getId()));
        if(ruList != null && ruList.size() > 0){
            List<Long> roleIdList = ruList.stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());
            List<SysRoleMenuEntity> rmList = sysRoleMenuService.list(new QueryWrapper<SysRoleMenuEntity>().in("role_id",roleIdList));
            if(rmList != null && rmList.size() > 0){
                List<Long> menuIdList = rmList.stream().map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList());
                List<SysMenuEntity> menuList = sysMenuService.list(new QueryWrapper<SysMenuEntity>().in("id",menuIdList));
                List<SysMenuVO> menuVOList = SysBeanUtil.copyList(menuList,SysMenuVO.class);
                map.put("menuList",menuVOList);
                /**
                 * 把用户菜单放session里
                 */
                subject.getSession().setAttribute(Constants.KEY_SESSION_SYS_USER_MENU,menuVOList);
            }
        }
        return buildSuccessInfo(map);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public WebResult getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        return buildSuccessInfo(subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_INFO));
    }


    @RequiresAuthentication
    @RequestMapping(value = "/getUserMenu", method = RequestMethod.GET)
    public WebResult getUserMenu(){
        Subject subject = SecurityUtils.getSubject();
        return buildSuccessInfo(subject.getSession().getAttribute(Constants.KEY_SESSION_SYS_USER_MENU));
    }




    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public WebResult logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return buildSuccessInfo(null);
    }

    @RequestMapping("/shiroUnauthorizedUrl")
    public WebResult shiroUnauthorizedUrl(){
        return buildFailedInfo("shiroUnauthorizedUrl");
    }

    @RequestMapping("/shiroLoginUrl")
    public WebResult shiroLoginUrl(){
        return buildFailedInfo("shiroLoginUrl");
    }



}
