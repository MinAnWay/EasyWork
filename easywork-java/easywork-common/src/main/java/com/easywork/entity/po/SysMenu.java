package com.easywork.entity.po;


import com.easywork.annotation.VerifyParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Description:菜单表
 * @author:AndrewWay
 * @date:2024/08/01
 */
@Data
public class SysMenu implements Serializable {
	private static final long serialVersionUID = -328320974058955602L;
	/**
	 * menu_id,自增主键
	 */
	private Integer menuId;
	private Integer menu_id;

	/**
	 * 菜单名
	 */
	@VerifyParam(required = true,max = 32)
	private String menuName;

	/**
	 * 菜单类型 0:菜单 1:按钮
	 */
	@VerifyParam(required = true)
	private Integer menuType;

	/**
	 * 菜单跳转到的地址
	 */
	private String menuUrl;

	/**
	 * 上级菜单ID
	 */
	@VerifyParam(required = true)
	private Integer pId;

	/**
	 * 菜单排序
	 */
	@VerifyParam(required = true)
	private Integer sort;

	/**
	 * 权限编码
	 */
	@VerifyParam(required = true,max = 50)
	private String permissionCode;

	/**
	 * 图标
	 */
	@VerifyParam(max = 50)
	private String icon;

	private List<SysMenu> children;
}