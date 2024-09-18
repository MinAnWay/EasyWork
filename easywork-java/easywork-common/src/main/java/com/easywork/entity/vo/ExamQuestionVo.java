package com.easywork.entity.vo;

import com.easywork.entity.po.ExamQuestionItem;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 19:20:41
 **/
public class ExamQuestionVo implements Serializable {
    private static final long serialVersionUID = -5285509140630744344L;

    /**
     * 考试ID
     * */
    private Integer examId;

    /**
     * 问题ID
     * */
    private Integer questionId;

    /**
     * 标题
     * */
    private String title;

    /**
     * 难度
     * */
    private Integer difficultyLevel;

    /**
     * 问题类型 0:判断 1:单选 2:多选
     * */
    private Integer questionType;

    /**
     * 问题描述
     * */
    private String question;

    /**
     * 问题答案
     * */
    private String questionAnswer;

    /**
     * 回答解析
     * */
    private String answerAnalysis;

    /**
     * 用户答案
     * */
    private String userAnswer;

    /**
     * 答案结果
     * */
    private Integer answerResult;

    private List<ExamQuestionItem> questionItemList;

    private Boolean haveCollect;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Integer getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(Integer answerResult) {
        this.answerResult = answerResult;
    }

    public List<ExamQuestionItem> getQuestionItemList() {
        return questionItemList;
    }

    public void setQuestionItemList(List<ExamQuestionItem> questionItemList) {
        this.questionItemList = questionItemList;
    }

    public Boolean getHaveCollect() {
        return haveCollect;
    }

    public void setHaveCollect(Boolean haveCollect) {
        this.haveCollect = haveCollect;
    }
}
