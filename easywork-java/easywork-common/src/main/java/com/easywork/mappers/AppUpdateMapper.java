package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;


/**
 * @Description:app发布Mapper
 * @author:AndrewWay
 * @date:2024/08/07
 */
public interface AppUpdateMapper<T, P> extends BaseMapper {
	/**
	 * 根据Id查询
	 */
	T selectById(@Param("id") Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateById(@Param("bean") T t, @Param("id") Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteById(@Param("id") Integer id);


    T selectLatestUpdate(@Param("version") String appVersion, @Param("deviceId") String deviceId);
}