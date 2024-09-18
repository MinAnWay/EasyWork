package com.easywork.entity.query;


import java.util.List;

/**
 * @Description:查询对象
 * @author:AndrewWay
 * @date:2024/08/03
 */
public class ExamQuestionItemQuery extends BaseQuery {
	/**
	 * 选项ID
	 */
	private Integer itemId;

	/**
	 * 问题ID
	 */
	private Integer questionId;

	/**
	 * 标题
	 */
	private String title;

	private String titleFuzzy;

	/**
	 * 排序
	 */
	private Integer sort;

	private List<String> questionIdList;

	public void setQuestionIdList(List<String> questionIdList) {
		this.questionIdList = questionIdList;
	}

	public List<String> getQuestionIdList() {
		return this.questionIdList;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemId() {
		return this.itemId;
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

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setTitleFuzzy(String titleFuzzy) {
		this.titleFuzzy = titleFuzzy;
	}

	public String getTitleFuzzy() {
		return this.titleFuzzy;
	}

}