package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 17:33:55
 **/
public enum AppExamStatusEnum {
    INIT(0, "未完成"), FINISH(1, "已完成");

    private Integer status;
    private String desc;

    AppExamStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
