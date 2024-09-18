package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/10 15:25:16
 **/
public enum RequestFrequencyTypeEnum {
    DAY(60 * 60 * 24, "一天"), HOUR(60 * 60, "一小时"), MINUTE(60, "一分钟"), SECOND(1, "一秒"), NO_LIMIT(0, "无限制");;

    private Integer seconds;
    private String desc;

    RequestFrequencyTypeEnum(Integer seconds, String desc) {
        this.seconds = seconds;
        this.desc = desc;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
