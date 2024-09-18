package com.easywork.mappers;

import com.easywork.entity.query.Role2MenuQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:角色菜单关联表Mapper
 * @author:AndrewWay
 * @date:2024/08/01
 */
public interface SysRole2MenuMapper<T, P> extends BaseMapper {
	/**
	 * 根据RoleIdAndMenuId查询
	 */
	T selectByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

	/**
	 * 根据RoleIdAndMenuId更新
	 */
	Integer updateByRoleIdAndMenuId(@Param("bean") T t, @Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

	/**
	 * 根据RoleIdAndMenuId删除
	 */
	Integer deleteByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);


	void deleteByParam(Role2MenuQuery role2MenuQuery);

	List<Integer> selectMenuIdsByRoleIds(@Param("roleIds") String[] roleIds);
}