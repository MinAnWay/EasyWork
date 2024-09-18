package com.easywork.entity.dto;

import com.easywork.entity.po.AppExamQuestion;

import java.util.List;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 19:21:22
 **/
public class AppExamPostDto {
    private Integer examId;
    private String remark;

    private List<AppExamQuestion> appExamQuestionList;

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<AppExamQuestion> getAppExamQuestionList() {
        return appExamQuestionList;
    }

    public void setAppExamQuestionList(List<AppExamQuestion> appExamQuestionList) {
        this.appExamQuestionList = appExamQuestionList;
    }
}
