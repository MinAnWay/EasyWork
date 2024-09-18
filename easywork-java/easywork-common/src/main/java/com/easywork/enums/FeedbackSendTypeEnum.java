package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/7 02:04:22
 **/
public enum FeedbackSendTypeEnum {
    CLIENT(0, "访客"), ADMIN(1, "管理员");

    private Integer status;
    private String desc;

    FeedbackSendTypeEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static FeedbackSendTypeEnum getByStatus(Integer status) {
        for (FeedbackSendTypeEnum value : FeedbackSendTypeEnum.values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }
}
