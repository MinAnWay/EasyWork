package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 17:45:38
 **/
public enum AnswerResultEnum {
    INIT(0, "未作答"),RIGHT(1, "正确"),WRONG(2, "错误");

    private Integer result;
    private String desc;

    AnswerResultEnum(Integer status, String desc) {
        this.result = status;
        this.desc = desc;
    }

    public Integer getResult() {
        return result;
    }

    public String getDesc() {
        return desc;
    }
}
