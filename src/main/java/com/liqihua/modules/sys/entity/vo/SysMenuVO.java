package com.liqihua.modules.sys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;


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

    @ApiModelProperty("父级id")
    private Long pid;

    @ApiModelProperty("菜单标题")
    private String title;

    @ApiModelProperty("vue的路由名称")
    private String routerName;

    @ApiModelProperty("1：一级菜单，2：二级菜单，3：三级菜单，...")
    private Integer level;

    @ApiModelProperty("1：隐藏，0：显示")
    private Boolean hide;

    @ApiModelProperty("排序")
    private Integer rankNum;

    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateDate;

    @ApiModelProperty("子菜单")
    private List<SysMenuVO> children;

    @ApiModelProperty("权限列表")
    private List<SysPermVO> permList;


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

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
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

    public List<SysPermVO> getPermList() {
        return permList;
    }

    public void setPermList(List<SysPermVO> permList) {
        this.permList = permList;
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
        ", rankNum=" + rankNum +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
