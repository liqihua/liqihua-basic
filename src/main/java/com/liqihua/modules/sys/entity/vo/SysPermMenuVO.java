package com.liqihua.modules.sys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * <p>
 * 菜单权限树形关系
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */


@ApiModel(value="SysPermMenuVO")
public class SysPermMenuVO {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("菜单id")
    private Long menuId;
    @ApiModelProperty("权限id")
    private Long permId;
    @ApiModelProperty("菜单")
    private SysMenuVO menu;
    @ApiModelProperty("权限")
    private SysPermVO perm;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public Long getPermId() {
        return permId;
    }

    public void setPermId(Long permId) {
        this.permId = permId;
    }


    public SysMenuVO getMenu() {
        return menu;
    }

    public void setMenu(SysMenuVO menu) {
        this.menu = menu;
    }

    public SysPermVO getPerm() {
        return perm;
    }

    public void setPerm(SysPermVO perm) {
        this.perm = perm;
    }
}
