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
 * 角色
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */


@ApiModel(value="SysRoleVO")
public class SysRoleVO {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;
    @ApiModelProperty("更新时间")
    private LocalDateTime updateDate;
    @ApiModelProperty("菜单列表")
    private List<SysMenuVO> menuList;
    @ApiModelProperty("权限列表")
    private List<SysPermVO> permList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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


    public List<SysMenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenuVO> menuList) {
        this.menuList = menuList;
    }

    public List<SysPermVO> getPermList() {
        return permList;
    }

    public void setPermList(List<SysPermVO> permList) {
        this.permList = permList;
    }

    @Override
    public String toString() {
        return "SysRoleEntity{" +
        "id=" + id +
        ", name=" + name +
        ", remarks=" + remarks +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
