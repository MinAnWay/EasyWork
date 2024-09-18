package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;


/**
 * @Description:考试题目Mapper
 * @author:AndrewWay
 * @date:2024/08/03
 */
public interface ExamQuestionMapper<T, P> extends BaseMapper {
	/**
	 * 根据QuestionId查询
	 */
	T selectByQuestionId(@Param("questionId") Integer questionId);

	/**
	 * 根据QuestionId更新
	 */
	Integer updateByQuestionId(@Param("bean") T t, @Param("questionId") Integer questionId);

	/**
	 * 根据QuestionId删除
	 */
	Integer deleteByQuestionId(@Param("questionId") Integer questionId);


    Integer deleteByParam(@Param("query") P examQuestionQuery);

	Integer updateByParam(@Param("bean") T bean, @Param("param") P param);

	T showDetailNext(@Param("query") P p);
}