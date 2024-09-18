package com.easywork.entity.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Description 用户信息vo
 *
 * @author Andrew
 * @date 2024/8/12 00:35:53
 **/
@Data
public class AppUserInfoVo implements Serializable {
    private static final long serialVersionUID = -3348413340306467920L;

    /**
     * 用户ID
     * */
    private String userId;

    /**
     * 邮箱
     * */
    private String email;

    /**
     * 昵称
     * */
    private String nickName;

    /**
     * 头像
     * */
    private String avatar;

    /**
     * 性别 0:女 1:男
     * */
    private Integer sex;

    /**
     * 创建时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date joinTime;

    /**
     * 最后登录时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date lastLoginTime;

    /**
     * 手机品牌
     * */
    private String lastUseDeviceBrand;
}
