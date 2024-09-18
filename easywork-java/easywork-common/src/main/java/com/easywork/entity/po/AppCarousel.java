package com.easywork.entity.po;


import java.io.Serializable;


/**
 * @Description:app轮播图
 * @author:AndrewWay
 * @date:2024/08/07
 */
public class AppCarousel implements Serializable {
	private static final long serialVersionUID = -193342215839315449L;
	/**
	 * 轮播图自增ID
	 */
	private Integer carouselId;

	/**
	 * 图片
	 */
	private String imgPath;

	/**
	 * 0:分享 1:问题 2:考题 3:外部链接
	 */
	private Integer objectType;

	/**
	 * 文章ID
	 */
	private String objectId;

	/**
	 * 外部链接
	 */
	private String outerLink;

	/**
	 * 排序
	 */
	private Integer sort;

	public void setCarouselId(Integer carouselId) {
		this.carouselId = carouselId;
	}

	public Integer getCarouselId() {
		return this.carouselId;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectType() {
		return this.objectType;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setOuterLink(String outerLink) {
		this.outerLink = outerLink;
	}

	public String getOuterLink() {
		return this.outerLink;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	@Override
	public String toString() {
		return "轮播图自增ID:" + (carouselId == null ? "空" : carouselId) + ",图片:" + (imgPath == null ? "空" : imgPath) + ",0:分享 1:问题 2:考题 3:外部链接:" + (objectType == null ? "空" : objectType) + ",文章ID:" + (objectId == null ? "空" : objectId) + ",外部链接:" + (outerLink == null ? "空" : outerLink) + ",排序:" + (sort == null ? "空" : sort);
	}
}