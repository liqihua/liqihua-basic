package com.liqihua.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import com.liqihua.common.constant.Constants;
import com.liqihua.common.utils.SysBeanUtil;
import com.liqihua.common.utils.SysFileUtil;
import com.liqihua.sys.entity.SysRoleEntity;
import com.liqihua.sys.entity.SysRoleUserEntity;
import com.liqihua.sys.entity.SysUserEntity;
import com.liqihua.sys.entity.vo.SysRoleVO;
import com.liqihua.sys.entity.vo.SysUserVO;
import com.liqihua.sys.service.SysRoleService;
import com.liqihua.sys.service.SysRoleUserService;
import com.liqihua.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
@RequestMapping("/sys/sysUserWebController")
public class SysUserWebController extends BaseController {


    @Value("${mvc.static.prefix}")
    private String prefix;


    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysRoleService sysRoleService;



    @RequiresPermissions("sysUser-setRole")
    @RequestMapping(value = "/setRoles", method = RequestMethod.POST)
    public WebResult setRoles(@RequestParam Long userId,
                              String roleIds) {
        sysRoleUserService.remove(new QueryWrapper<SysRoleUserEntity>().eq("user_id",userId));
        if(StrUtil.isNotBlank(roleIds)){
            String[] roleIdArr = roleIds.split(",");
            if(roleIdArr != null && roleIdArr.length > 0){
                for(String _roleId : roleIdArr) {
                    SysRoleUserEntity ru = new SysRoleUserEntity(Long.valueOf(_roleId),userId);
                    sysRoleUserService.save(ru);
                }
            }
        }
        /**
         * 刷新用户权限和角色
         */
        sysUserService.refreshRealm();
        return buildSuccessInfo(null);
    }


    @RequiresPermissions("sysUser-save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public WebResult save(Long id,
                          @RequestParam String username,
                          @RequestParam Boolean locked,
                          String password,
                          String nickname,
                          String realName,
                          Boolean gender,
                          String avatar,
                          String mobile,
                          String remarks){
        SysUserEntity entity = null;
        if(id != null){
            entity = sysUserService.getById(id);
            if(entity == null){
                return buildFailedInfo(ApiConstant.USER_NOT_EXIST);
            }
        }else{
            entity = new SysUserEntity();
        }
        if(StrUtil.isNotBlank(avatar)){
            avatar = avatar.replace(prefix,"");
        }
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setNickname(nickname);
        entity.setRealName(realName);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setMobile(mobile);
        entity.setRemarks(remarks);
        entity.setLocked(locked);
        sysUserService.saveOrUpdate(entity);
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }


    @RequiresPermissions("sysUser-delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public WebResult delete(@RequestParam Long id){
        boolean delete = sysUserService.removeById(id);
        sysRoleUserService.remove(new QueryWrapper<SysRoleUserEntity>().eq("user_id",id));
        Subject subject = SecurityUtils.getSubject();
        SysUserEntity sysUser = (SysUserEntity)subject.getPrincipal();
        if(id.equals(sysUser.getId())){
            subject.logout();
        }
        return buildSuccessInfo(delete);
    }



    @RequiresPermissions("sysUser-list")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public WebResult page(@RequestParam Integer page,
                          @RequestParam Integer pageSize,
                          String username,
                          String password,
                          String nickname,
                          String realName,
                          Boolean gender,
                          String avatar,
                          String mobile,
                          String remarks,
                          Boolean lock){
        SysUserEntity entity = new SysUserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setNickname(nickname);
        entity.setRealName(realName);
        entity.setGender(gender);
        entity.setAvatar(avatar);
        entity.setMobile(mobile);
        entity.setRemarks(remarks);
        entity.setLocked(lock);
        QueryWrapper queryWrapper = new QueryWrapper<SysUserEntity>(entity);
        IPage result = sysUserService.page(new Page<SysUserEntity>(page,pageSize),queryWrapper);
        List<SysUserVO> voList = SysBeanUtil.copyList(result.getRecords(),SysUserVO.class);
        voList.forEach(vo -> {
            if(StrUtil.isNotBlank(vo.getAvatar()) && vo.getAvatar().startsWith("/")){
                vo.setAvatar(prefix + vo.getAvatar());
            }
            vo = makeVO(vo);
        });
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }



    @RequiresAuthentication
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public WebResult get(@RequestParam Long id){
        SysUserEntity entity = sysUserService.getById(id);
        SysUserVO vo = null;
        if(entity != null){
            vo = new SysUserVO();
            BeanUtils.copyProperties(entity,vo);
            if(StrUtil.isNotBlank(vo.getAvatar()) && vo.getAvatar().startsWith("/")){
                vo.setAvatar(prefix + vo.getAvatar());
            }
        }
        vo = makeVO(vo);
        return buildSuccessInfo(vo);
    }




    @RequiresAuthentication
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public WebResult uploadAvatar(@RequestParam MultipartFile avatar){
        String path = SysFileUtil.uploadFile(avatar,null);
        return buildSuccessInfo(prefix + path);
    }



    public SysUserVO makeVO(SysUserVO vo) {
        if(vo != null && vo.getId() != null) {
            List<SysRoleUserEntity> ruList = sysRoleUserService.list(new QueryWrapper<SysRoleUserEntity>().eq("user_id",vo.getId()));
            if(ruList != null && ruList.size() > 0) {
                List<Long> roleIdList = ruList.stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());
                List<SysRoleEntity> roleList = sysRoleService.list(new QueryWrapper<SysRoleEntity>().in("id",roleIdList));
                List<SysRoleVO> roleVOList = SysBeanUtil.copyList(roleList,SysRoleVO.class);
                vo.setRoleList(roleVOList);
            }
        }
        return vo;
    }

}
