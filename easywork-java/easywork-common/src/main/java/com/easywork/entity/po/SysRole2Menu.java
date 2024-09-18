package com.easywork.entity.po;


import lombok.Data;

import java.io.Serializable;


/**
 * @Description:角色菜单关联表
 * @author:AndrewWay
 * @date:2024/08/01
 */
@Data
public class SysRole2Menu implements Serializable {
	private static final long serialVersionUID = 2875578973696252274L;
	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 菜单ID
	 */
	private Integer menuId;

	/**
	 * 0:半选 1:全选
	 */
	private Integer checkType;
}