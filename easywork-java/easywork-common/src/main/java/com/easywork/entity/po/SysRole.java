package com.easywork.entity.po;

import com.easywork.annotation.VerifyParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Description:系统角色表
 * @author:AndrewWay
 * @date:2024/08/01
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = -5019696346305153262L;
    /**
     * 角色ID
     */
    private Integer roleId;


    /**
     * 角色名称
     */
    @VerifyParam(required = true, max = 100)
    private String roleName;

    /**
     * 角色描述
     */
    @VerifyParam(required = true, max = 255)
    private String roleDesc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date lastUpdateTime;

    private List<Integer> menuIds;
}