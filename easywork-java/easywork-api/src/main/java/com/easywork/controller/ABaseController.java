package com.easywork.controller;

import com.easywork.entity.config.AppConfig;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.utils.JWTUtil;
import com.easywork.utils.StringTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

;
;

public class ABaseController {

    @Resource
    private AppConfig appConfig;

    @Resource
    private JWTUtil<AppUserLoginDto> jwtUtil;

    protected static final String STATUS_SUCCESS = "success";

    protected static final String STATUS_ERROR = "error";

    protected <T> ResponseVo getSuccessResponseVO(T t) {
        ResponseVo<T> responseVo = new ResponseVo<>();
        responseVo.setStatus(STATUS_SUCCESS);
        responseVo.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVo.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVo.setData(t);
        return responseVo;
    }

    protected AppUserLoginDto getAppUserLoginInfoFromToken(String token) throws BusinessException {
        AppUserLoginDto loginDto = jwtUtil.getTokenData(Constants.JWT_KEY_LOGIN_TOKEN, token, AppUserLoginDto.class);
        return loginDto;
    }

    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实的
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected String resetContentImg(String content){
        if (StringTools.isEmpty(content)){
            return content;
        }
        content = content.replace(Constants.READ_IMAGE_PATH,appConfig.getAppDomain()+Constants.READ_IMAGE_PATH);
        return content;
    }
}
