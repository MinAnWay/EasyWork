package com.easywork.entity.query;

import lombok.Data;

import java.util.Date;


/**
 * @Description:账号信息查询对象
 * @author:AndrewWay
 * @date:2024/07/31
 */
@Data
public class AccountQuery extends BaseQuery {
	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 手机号
	 */
	private String phone;

	private String phoneFuzzy;

	/**
	 * 用户名
	 */
	private String userName;

	private String userNameFuzzy;

	/**
	 * 密码
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 职位
	 */
	private String position;

	private String positionFuzzy;

	/**
	 * 状态 0：禁用 1：启用
	 */
	private Integer status;

	/**
	 * 用户拥有的角色多个用逗号隔开
	 */
	private String roles;

	private String rolesFuzzy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	private Boolean queryRoles;

}