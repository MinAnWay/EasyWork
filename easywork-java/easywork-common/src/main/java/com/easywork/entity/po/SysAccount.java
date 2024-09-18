package com.easywork.entity.po;

import com.easywork.annotation.VerifyParam;
import com.easywork.enums.VerifyRegexEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:账号信息
 * @author:AndrewWay
 * @date:2024/07/31
 */
@Data
public class SysAccount implements Serializable {
    private static final long serialVersionUID = 5995928776702809814L;
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    @VerifyParam(required = true, regex = VerifyRegexEnum.PHONE)
    private String phone;

    /**
     * 用户名
     */
    @VerifyParam(required = true, max = 20)
    private String userName;

    /**
     * 密码
     */
    @JsonIgnore
    @VerifyParam(regex = VerifyRegexEnum.PASSWORD)
    private String password;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态 0：禁用 1：启用
     */
    private Integer status;

    /**
     * 用户拥有的角色多个用逗号隔开
     */
    @VerifyParam(required = true)
    private String roles;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    private String roleNames;
}