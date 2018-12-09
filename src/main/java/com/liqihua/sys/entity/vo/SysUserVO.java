package com.liqihua.sys.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * <p>
 *  用户
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */


@ApiModel(value="SysUserVO")
public class SysUserVO {


    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("1：男，0：女")
    private Boolean gender;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("1：冻结，0：正常")
    private Boolean locked;
    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;
    @ApiModelProperty("更新时间")
    private LocalDateTime updateDate;
    @ApiModelProperty("角色列表")
    private List<SysRoleVO> roleList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public List<SysRoleVO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRoleVO> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "SysUserEntity{" +
        "id=" + id +
        ", username=" + username +
        ", nickname=" + nickname +
        ", realName=" + realName +
        ", gender=" + gender +
        ", avatar=" + avatar +
        ", mobile=" + mobile +
        ", remarks=" + remarks +
        ", locked=" + locked +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
