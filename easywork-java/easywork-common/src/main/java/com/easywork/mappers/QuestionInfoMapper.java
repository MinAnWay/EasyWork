package com.easywork.mappers;

import com.easywork.entity.po.QuestionInfo;
import com.easywork.entity.query.QuestionInfoQuery;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:问题Mapper
 * @author:AndrewWay
 * @date:2024/08/03
 */
public interface QuestionInfoMapper<T, P> extends BaseMapper {
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


    void delBatchByQuestionId(@Param("questionIdArray") String[] questionIdArray, @Param("status") Integer status, @Param("userId") Integer userId);

    void updateByParams(@Param("bean") QuestionInfo bean, @Param("params") QuestionInfoQuery params);

    T showDetailNext(@Param("query") P p);
}