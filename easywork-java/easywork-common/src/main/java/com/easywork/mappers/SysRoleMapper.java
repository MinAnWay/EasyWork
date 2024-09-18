package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;


/**
 * @Description:系统角色表Mapper
 * @author:AndrewWay
 * @date:2024/08/01
 */
public interface SysRoleMapper<T, P> extends BaseMapper {
	/**
	 * 根据RoleId查询
	 */
	T selectByRoleId(@Param("roleId") Integer roleId);

	/**
	 * 根据RoleId更新
	 */
	Integer updateByRoleId(@Param("bean") T t, @Param("roleId") Integer roleId);

	/**
	 * 根据RoleId删除
	 */
	Integer deleteByRoleId(@Param("roleId") Integer roleId);


}