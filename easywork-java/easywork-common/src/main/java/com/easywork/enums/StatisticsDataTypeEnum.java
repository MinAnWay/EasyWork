package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/7 17:15:55
 **/
public enum StatisticsDataTypeEnum {
    APP_DOWNLOAD(0, "app下载"), REGISTER_USER(1, "注册用户"), QUESTION_INFO(3, "八股文"),
    EXAM(4, "考题"), SHARE(5, "分享"), FEEDBACK(6, "反馈");

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    StatisticsDataTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static StatisticsDataTypeEnum getByType(Integer type) {
        for (StatisticsDataTypeEnum statisticsDataTypeEnum : StatisticsDataTypeEnum.values()) {
            if (statisticsDataTypeEnum.getType().equals(type)) {
                return statisticsDataTypeEnum;
            }
        }
        return null;
    }
}
