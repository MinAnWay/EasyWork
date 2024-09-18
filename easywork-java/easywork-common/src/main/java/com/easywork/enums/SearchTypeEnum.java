package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 01:25:21
 **/
public enum SearchTypeEnum {
    QUESTION(0, "搜索问题"), EXAM_QUESTION(1, "搜索考题"), SHARE(2, "分享");

    private Integer type;
    private String desc;

    SearchTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static SearchTypeEnum getByType(Integer type) {
        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {
            if (searchTypeEnum.getType().equals(type)) {
                return searchTypeEnum;
            }
        }
        return null;
    }
}
