package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;


/**
 * @Description:用户在线考试Mapper
 * @author:AndrewWay
 * @date:2024/08/11
 */
public interface AppExamMapper<T, P> extends BaseMapper {
	/**
	 * 根据ExamId查询
	 */
	T selectByExamId(@Param("examId") Integer examId);

	/**
	 * 根据ExamId更新
	 */
	Integer updateByExamId(@Param("bean") T t, @Param("examId") Integer examId);

	/**
	 * 根据ExamId删除
	 */
	Integer deleteByExamId(@Param("examId") Integer examId);

	Integer updateByParam(@Param("bean")T bean, @Param("param") P param);
}