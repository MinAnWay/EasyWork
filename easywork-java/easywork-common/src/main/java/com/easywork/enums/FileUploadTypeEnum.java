package com.easywork.enums;

/**
 * @author Andrew
 * @date 2024/8/3 23:28:22
 **/
public enum FileUploadTypeEnum {
    CATEGORY(0, 1500, "分类图片"),
    CAROUSEL(1, 400, "轮播图"),
    SHARE_LARGE(2, 400, "分享大图"),
    SHARE_SMALL(3, 100, "分享小图");

    private Integer type;
    private Integer maxWidth;
    private String desc;

    FileUploadTypeEnum(Integer type, Integer maxWidth, String desc) {
        this.type = type;
        this.maxWidth = maxWidth;
        this.desc = desc;
    }

    public static FileUploadTypeEnum getByType(Integer type) {
        for (FileUploadTypeEnum at : FileUploadTypeEnum.values()) {
            if (at.type.equals(type)) {
                return at;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public String getDesc() {
        return desc;
    }
}
