package com.easywork.enums;

public enum CategoryTypeEnum {
    QUESTION(0, "八股文分类"),
    EXAM(1, "考试分类"),
    QUESTION_EXAM(2, "八股文分类和考题分类"),
    ;

    private Integer type;
    private String desc;

    CategoryTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CategoryTypeEnum getByType(Integer type) {
        for (CategoryTypeEnum item : values()) {
            if (item.getType() == type) {
                return item;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
