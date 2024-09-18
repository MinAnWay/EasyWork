package com.easywork.entity.po;

import com.easywork.annotation.VerifyParam;
import com.easywork.enums.DateTimePatternEnum;
import com.easywork.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Description:考试题目
 * @author:AndrewWay
 * @date:2024/08/03
 */
public class ExamQuestion implements Serializable {
	private static final long serialVersionUID = -6788619024723888686L;
	/**
	 * 问题ID
	 */
	private Integer questionId;

	/**
	 * 标题
	 */
	@VerifyParam(required = true)
	private String title;

	/**
	 * 分类ID
	 */
	@VerifyParam(required = true)
	private Integer categoryId;

	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 难度
	 */
	@VerifyParam(required = true)
	private Integer difficultyLevel;

	/**
	 * 问题类型：0-判断题、1-单选题、2-多选题
	 */
	@VerifyParam(required = true)
	private Integer questionType;

	/**
	 * 问题描述
	 */
	private String questionText;

	/**
	 * 答案
	 */
	@VerifyParam(required = true)
	private String questionAnswer;

	/**
	 * 回答解释
	 */
	@VerifyParam(required = true)
	private String answerAnalysis;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date createTime;

	/**
	 * 状态：0-未发布、1-已发布
	 */
//	@JsonIgnore
	private Integer status;

	/**
	 * 创建用户ID
	 */
	private String createUserId;

	/**
	 * 创建用户昵称
	 */
	private String createUserName;

	/**
	 * 投稿类型：0-内部、1-外部投稿
	 */
	private Integer postUserType;

	List<ExamQuestionItem> questionItemList;

	private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<ExamQuestionItem> getQuestionItemList() {
		return questionItemList;
	}

	public void setQuestionItemList(List<ExamQuestionItem> questionItemList) {
		this.questionItemList = questionItemList;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setDifficultyLevel(Integer difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public Integer getDifficultyLevel() {
		return this.difficultyLevel;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionType() {
		return this.questionType;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getQuestionText() {
		return this.questionText;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getQuestionAnswer() {
		return this.questionAnswer;
	}

	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}

	public String getAnswerAnalysis() {
		return this.answerAnalysis;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setPostUserType(Integer postUserType) {
		this.postUserType = postUserType;
	}

	public Integer getPostUserType() {
		return this.postUserType;
	}

	@Override
	public String toString() {
		return "问题ID:" + (questionId == null ? "空" : questionId) + ",标题:" + (title == null ? "空" : title) + ",分类ID:" + (categoryId == null ? "空" : categoryId) + ",分类名称:" + (categoryName == null ? "空" : categoryName) + ",难度:" + (difficultyLevel == null ? "空" : difficultyLevel) + ",问题类型：0-判断题、1-单选题、2-多选题:" + (questionType == null ? "空" : questionType) + ",问题描述:" + (questionText == null ? "空" : questionText) + ",答案:" + (questionAnswer == null ? "空" : questionAnswer) + ",回答解释:" + (answerAnalysis == null ? "空" : answerAnalysis) + ",创建时间:" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",状态：0-未发布、1-已发布:" + (status == null ? "空" : status) + ",创建用户ID:" + (createUserId == null ? "空" : createUserId) + ",创建用户昵称:" + (createUserName == null ? "空" : createUserName) + ",投稿类型：0-内部、1-外部投稿:" + (postUserType == null ? "空" : postUserType);
	}
}