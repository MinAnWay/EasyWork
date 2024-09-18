package com.easywork.enums;

/**
 * @author Andrew
 * @date 2024/8/4 18:26:01
 **/
public enum PostStatusEnum {
    NO_POST(0, "未发布"),
    POST(1, "已发布"),
    ;

    private Integer status;
    private String desc;

    PostStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static PostStatusEnum getByStatus(Integer status) {
        for (PostStatusEnum at : PostStatusEnum.values()) {
            if (at.getStatus().equals(status)) {
                return at;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
