package com.easywork.enums;

/**
 * @author Andrew
 * @date 2024/8/4 15:13:15
 **/
public enum ImportTemplateTypeEnum {
    QUESTION(0, "/template/template_question.xlsx", "问题模板.xlsx"),
    QUESTION_EXAM(1, "/template/template_exam.xlsx", "试题库模板.xlsx");

    private Integer type;
    private String templatePath;
    private String templateName;

    public Integer getType() {
        return type;
    }


    public String getTemplatePath() {
        return templatePath;
    }


    public String getTemplateName() {
        return templateName;
    }


    ImportTemplateTypeEnum(Integer type, String templatePath, String templateName) {
        this.type = type;
        this.templatePath = templatePath;
        this.templateName = templateName;
    }

    public static ImportTemplateTypeEnum getByType(Integer type) {
        for (ImportTemplateTypeEnum item : ImportTemplateTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
