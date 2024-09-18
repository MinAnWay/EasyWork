package com.easywork.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件
 */
@Configuration
public class AppConfig {
    @Value("${project.folder}")
    private String projectFolder;

    @Value("${jwt.common.secret:}")
    private String jwtCommonSecret;

    @Value("${super.admin.phones}")
    private String superAdminPhones;

    @Value("${app.name:EasyJob}")
    private String appName;

    @Value("${app.domain:}")
    private String getAppDomain;

    public String getAppDomain() {
        return getAppDomain;
    }

    public String getAppName() {
        return appName;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getJwtCommonSecret() {
        return jwtCommonSecret;
    }

    public String getSuperAdminPhones() {
        return superAdminPhones;
    }
}
