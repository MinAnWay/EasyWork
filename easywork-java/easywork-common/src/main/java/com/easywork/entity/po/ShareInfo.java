package com.easywork.entity.po;

import com.easywork.enums.DateTimePatternEnum;
import com.easywork.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:文章分享
 * @author:AndrewWay
 * @date:2024/08/03
 */
public class ShareInfo implements Serializable {
	private static final long serialVersionUID = -5722559870034725089L;
	/**
	 * 自增ID
	 */
	private Integer shareId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 封面路径
	 */
	private String coverPath;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date createTime;

	/**
	 * 状态：0-已发布
	 */
//	@JsonIgnore
	private Integer status;

	/**
	 * 用户ID
	 */
	private String createUserId;

	/**
	 * 姓名
	 */
	private String createUserName;

	/**
	 * 阅读数量
	 */
	private Integer readCount;

	/**
	 * 收藏数
	 */
	private Integer collectCount;

	/**
	 * 投稿类型：0-内部、1-外部投稿
	 */
	private Integer postUserType;

	private Integer coverType;

	private Boolean haveCollect;

	private Integer collectId;

	public Integer getCollectId() {
		return collectId;
	}


	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}
	public Boolean getHaveCollect() {
		return haveCollect;
	}

	public void setHaveCollect(Boolean haveCollect) {
		this.haveCollect = haveCollect;
	}

	public void setCoverType(Integer coverType) {
		this.coverType = coverType;
	}

	public Integer getCoverType() {
		return coverType;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public Integer getShareId() {
		return this.shareId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getCoverPath() {
		return this.coverPath;
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

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getReadCount() {
		return this.readCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public Integer getCollectCount() {
		return this.collectCount;
	}

	public void setPostUserType(Integer postUserType) {
		this.postUserType = postUserType;
	}

	public Integer getPostUserType() {
		return this.postUserType;
	}

	@Override
	public String toString() {
		return "自增ID:" + (shareId == null ? "空" : shareId) + ",标题:" + (title == null ? "空" : title) + ",封面路径:" + (coverPath == null ? "空" : coverPath) + ",内容:" + (content == null ? "空" : content) + ",创建时间:" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",状态：0-已发布:" + (status == null ? "空" : status) + ",用户ID:" + (createUserId == null ? "空" : createUserId) + ",姓名:" + (createUserName == null ? "空" : createUserName) + ",阅读数量:" + (readCount == null ? "空" : readCount) + ",收藏数:" + (collectCount == null ? "空" : collectCount) + ",投稿类型：0-内部、1-外部投稿:" + (postUserType == null ? "空" : postUserType);
	}
}