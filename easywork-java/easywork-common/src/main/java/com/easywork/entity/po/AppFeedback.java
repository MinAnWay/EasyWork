package com.easywork.entity.po;

import com.easywork.enums.DateTimePatternEnum;
import com.easywork.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:问题反馈
 * @author:AndrewWay
 * @date:2024/08/07
 */
public class AppFeedback implements Serializable {
	private static final long serialVersionUID = 6556833260225820579L;
	/**
	 * 反馈自增ID
	 */
	private Integer feedbackId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 反馈内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date createTime;

	/**
	 * 父级反馈ID
	 */
	private Integer pFeedbackId;

	/**
	 * 状态 0:未回复 1:已回复
	 */
//	@JsonIgnore
	private Integer status;

	/**
	 * 0:访客 1:管理员
	 */
	private Integer sendType;

	/**
	 * 访客最后发送时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date clientLastSendTime;

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Integer getFeedbackId() {
		return this.feedbackId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setPFeedbackId(Integer pFeedbackId) {
		this.pFeedbackId = pFeedbackId;
	}

	public Integer getPFeedbackId() {
		return this.pFeedbackId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getSendType() {
		return this.sendType;
	}

	public void setClientLastSendTime(Date clientLastSendTime) {
		this.clientLastSendTime = clientLastSendTime;
	}

	public Date getClientLastSendTime() {
		return this.clientLastSendTime;
	}

	@Override
	public String toString() {
		return "反馈自增ID:" + (feedbackId == null ? "空" : feedbackId) + ",用户ID:" + (userId == null ? "空" : userId) + ",昵称:" + (nickName == null ? "空" : nickName) + ",反馈内容:" + (content == null ? "空" : content) + ",创建时间:" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",父级反馈ID:" + (pFeedbackId == null ? "空" : pFeedbackId) + ",状态 0:未回复 1:已回复:" + (status == null ? "空" : status) + ",0:访客 1:管理员:" + (sendType == null ? "空" : sendType) + ",访客最后发送时间:" + (clientLastSendTime == null ? "空" : DateUtils.format(clientLastSendTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}