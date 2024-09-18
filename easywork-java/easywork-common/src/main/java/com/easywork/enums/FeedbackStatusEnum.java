package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/7 02:04:38
 **/
public enum FeedbackStatusEnum {
    NO_REPLY(0, "未回复"),REPLY(1, "已回复");

    private Integer status;
    private String desc;

    FeedbackStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static FeedbackStatusEnum getByStatus(Integer status) {
        for (FeedbackStatusEnum feedbackStatusEnum : FeedbackStatusEnum.values()) {
            if (feedbackStatusEnum.getStatus().equals(status)) {
                return feedbackStatusEnum;
            }
        }
        return null;
    }
}
