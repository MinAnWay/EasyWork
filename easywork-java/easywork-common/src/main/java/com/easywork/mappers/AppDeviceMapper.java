package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;


/**
 * @Description:设备信息Mapper
 * @author:AndrewWay
 * @date:2024/08/07
 */
public interface AppDeviceMapper<T, P> extends BaseMapper {
	/**
	 * 根据DeviceId查询
	 */
	T selectByDeviceId(@Param("deviceId") String deviceId);

	/**
	 * 根据DeviceId更新
	 */
	Integer updateByDeviceId(@Param("bean") T t, @Param("deviceId") String deviceId);

	/**
	 * 根据DeviceId删除
	 */
	Integer deleteByDeviceId(@Param("deviceId") String deviceId);


}