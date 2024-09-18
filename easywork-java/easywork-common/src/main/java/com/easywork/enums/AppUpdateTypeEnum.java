package com.easywork.enums;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/7 16:38:13
 **/
public enum AppUpdateTypeEnum {
    ALL(0,".apk","全更新"),WGT(1,".wgt","局部热更新");

    private Integer type;
    private String suffix;
    private String desc;

    AppUpdateTypeEnum(Integer type, String suffix, String desc) {
        this.type = type;
        this.suffix = suffix;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getDesc() {
        return desc;
    }

    public static AppUpdateTypeEnum getByType(Integer type) {
        for (AppUpdateTypeEnum appUpdateTypeEnum : AppUpdateTypeEnum.values()) {
            if (appUpdateTypeEnum.getType().equals(type)) {
                return appUpdateTypeEnum;
            }
        }
        return null;
    }
}
