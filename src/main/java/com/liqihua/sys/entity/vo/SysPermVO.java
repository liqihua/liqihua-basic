package com.liqihua.sys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * <p>
 * 权限
 * </p>
 *
 * @author liqihua
 * @since 2018-11-07
 */


@ApiModel(value="SysPermVO")
public class SysPermVO {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("shiro符号")
    private String symbol;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("菜单")
    private SysMenuVO menu;

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
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public SysMenuVO getMenu() {
        return menu;
    }

    public void setMenu(SysMenuVO menu) {
        this.menu = menu;
    }
}
