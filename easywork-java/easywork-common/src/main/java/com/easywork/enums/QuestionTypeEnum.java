package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/5 23:36:37
 **/
public enum QuestionTypeEnum {
    TRUE_FALSE(0, "判断题"),
    RADIO(1, "单选题"),
    CHECK_BOX(2, "多选题");

    private Integer type;
    private String desc;

    QuestionTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static QuestionTypeEnum getByDesc(String desc) {
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            if (item.getDesc().equals(desc)) {
                return item;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
