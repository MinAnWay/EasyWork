package com.easywork.entity.vo.app;

import com.easywork.entity.vo.ExamQuestionVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 19:08:23
 **/
public class AppExamVo implements Serializable {
    private static final long serialVersionUID = -2593782408979833519L;
    /**
     * 自增ID
     */
    private Integer examId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date endTime;

    /**
     * 0:未完成 1:已完成
     */
    @JsonIgnore
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用时分钟
     */
    private BigDecimal useTimeMin;

    private List<ExamQuestionVo> examQuestionList;

    public List<ExamQuestionVo> getExamQuestionList() {
        return examQuestionList;
    }

    public void setExamQuestionList(List<ExamQuestionVo> examQuestionList) {
        this.examQuestionList = examQuestionList;
    }

    public BigDecimal getUseTimeMin() {
        if (this.endTime != null && this.startTime != null) {
            return new BigDecimal(endTime.getTime() - startTime.getTime()).divide(new BigDecimal(1000 * 60), 2, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal(0);
    }

    public void setUseTimeMin(BigDecimal useTimeMin) {
        this.useTimeMin = useTimeMin;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getExamId() {
        return this.examId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    @Override
    public String toString() {
        return "AppExamVo{" +
                "examId=" + examId +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", useTimeMin=" + useTimeMin +
                '}';
    }
}
