package com.easywork.entity.vo;

import java.io.Serializable;
import java.util.List;

public class SysMenuVo implements Serializable {
    private static final long serialVersionUID = 2179040093724381673L;
    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单跳转到的地址
     */
    private String menuUrl;

    /**
     * 图标
     */
    private String icon;

    private List<SysMenuVo> children;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<SysMenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuVo> children) {
        this.children = children;
    }
}
