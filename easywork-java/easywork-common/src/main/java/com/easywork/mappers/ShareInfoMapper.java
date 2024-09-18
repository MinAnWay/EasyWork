package com.easywork.mappers;

import com.easywork.entity.po.ShareInfo;
import com.easywork.entity.query.ShareInfoQuery;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:文章分享Mapper
 * @author:AndrewWay
 * @date:2024/08/03
 */
public interface ShareInfoMapper<T, P> extends BaseMapper {
	/**
	 * 根据ShareId查询
	 */
	T selectByShareId(@Param("shareId") Integer shareId);

	/**
	 * 根据ShareId更新
	 */
	Integer updateByShareId(@Param("bean") T t, @Param("shareId") Integer shareId);

	/**
	 * 根据ShareId删除
	 */
	Integer deleteByShareId(@Param("shareId") Integer shareId);


    void delByParam(@Param("query") P query);

	void updateByParam(@Param("bean")ShareInfo bean, @Param("param")ShareInfoQuery param);

	T showDetailNext(@Param("query") P p);
}