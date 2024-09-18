package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:菜单表Mapper
 * @author:AndrewWay
 * @date:2024/08/01
 */
public interface SysMenuMapper<T, P> extends BaseMapper {
	/**
	 * 根据MenuId查询
	 */
	T selectByMenuId(@Param("menuId") Integer menuId);

	/**
	 * 根据MenuId更新
	 */
	Integer updateByMenuId(@Param("bean") T t, @Param("menuId") Integer menuId);

	/**
	 * 根据MenuId删除
	 */
	Integer deleteByMenuId(@Param("menuId") Integer menuId);


    List<T> selectAllMenuByRoleIds(@Param("roleIds") int[] roleIds);
}