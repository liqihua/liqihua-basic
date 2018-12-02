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
 * 菜单
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */


@ApiModel(value="SysMenuVO")
public class SysMenuVO {


    private Long id;

    @ApiModelProperty(value = "父级id")
    private Long pid;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "vue的路由名称")
    private String routerName;

    @ApiModelProperty(value = "1：一级菜单，2：二级菜单，3：三级菜单，...")
    private Integer level;

    @ApiModelProperty(value = "1：隐藏，0：显示")
    private Boolean hide;

    @ApiModelProperty(value = "排序")
    private Integer rank;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    private List<SysMenuVO> children;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public List<SysMenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "SysMenuEntity{" +
        "id=" + id +
        ", pid=" + pid +
        ", title=" + title +
        ", routerName=" + routerName +
        ", level=" + level +
        ", hide=" + hide +
        ", rank=" + rank +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
