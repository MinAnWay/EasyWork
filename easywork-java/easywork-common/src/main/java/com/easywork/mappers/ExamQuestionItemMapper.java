package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:Mapper
 * @author:AndrewWay
 * @date:2024/08/03
 */
public interface ExamQuestionItemMapper<T, P> extends BaseMapper {
	/**
	 * 根据ItemId查询
	 */
	T selectByItemId(@Param("itemId") Integer itemId);

	/**
	 * 根据ItemId更新
	 */
	Integer updateByItemId(@Param("bean") T t, @Param("itemId") Integer itemId);

	/**
	 * 根据ItemId删除
	 */
	Integer deleteByItemId(@Param("itemId") Integer itemId);

	void deleteBatch(@Param("itemIdList")List<Integer> itemIdList);

	void deleteBatchQuestionId(@Param("questionIdArray")String[] questionIdArray,@Param("status")Integer status,@Param("userId")Integer userId);



}