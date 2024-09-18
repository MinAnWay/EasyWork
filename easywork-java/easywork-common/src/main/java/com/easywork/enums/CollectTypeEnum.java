package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/10 17:46:31
 **/
public enum CollectTypeEnum {
    SHARE(0, "分享收藏"),
    QUESTION(1, "八股文"),
    EXAM(2, "考题"),
    ;

    private Integer type;
    private String desc;

    CollectTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static CollectTypeEnum getByType(Integer type) {
        for (CollectTypeEnum collectTypeEnum : CollectTypeEnum.values()) {
            if (collectTypeEnum.getType().equals(type)) {
                return collectTypeEnum;
            }
        }
        return null;
    }
}
