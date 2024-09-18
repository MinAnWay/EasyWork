package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.component.RedisUtils;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.CreateImageCode;
import com.easywork.entity.po.AppUserInfo;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.VerifyRegexEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppUserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description  登录注册
 *
 * @author Andrew
 * @date 2024/8/8 15:32:54
 **/
@RestController()
@RequestMapping("/account")
public class LoginController extends ABaseController {
    @Resource
    private AppUserInfoService appUserInfoService;

    @Resource
    private RedisUtils redisUtils;

    private static final Integer CHECK_CODE_TYPE_REGISTER = 0;
    private static final Integer CHECK_CODE_TYPE_LOGIN = 1;

    @RequestMapping("/checkCode")
    @GlobalInterceptor
    public void checkCode(HttpServletResponse response, @VerifyParam(required = true) String deviceId,
                          @VerifyParam(required = true) Integer type) throws IOException {
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();
        String redisKey = Constants.REDIS_KEY_CHECKCODE + deviceId + type;
        redisUtils.setex(redisKey, code, 10 * 60);
        vCode.write(response.getOutputStream());
    }

    @RequestMapping("/register")
    @GlobalInterceptor
    public ResponseVo register(HttpServletRequest request, @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL) String email,
                               @VerifyParam(required = true, max = 30) String nickName,
                               @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD) String password,
                               @VerifyParam(required = true) Integer sex,
                               @VerifyParam(required = true) String checkCode,
                               @VerifyParam(required = true, max = 32) String deviceId,
                               @VerifyParam(max = 30) String deviceBrand) throws BusinessException {
        String redisKey = Constants.REDIS_KEY_CHECKCODE + deviceId + CHECK_CODE_TYPE_REGISTER;
        try {
            String checkCodeRedis = (String) redisUtils.get(redisKey);
            if (!checkCode.equalsIgnoreCase(checkCodeRedis)) {
                throw new BusinessException("图片验证码错误");
            }
            AppUserInfo appUserInfo = new AppUserInfo();
            appUserInfo.setEmail(email);
            appUserInfo.setSex(sex);
            appUserInfo.setPassword(password);
            appUserInfo.setNickname(nickName);
            appUserInfo.setLastUseDeviceId(deviceId);
            appUserInfo.setLastUseDeviceBrand(deviceBrand);
            appUserInfo.setLastLoginIp(getIpAddr(request));
            appUserInfoService.register(appUserInfo);
        } finally {
            redisUtils.delete(redisKey);
        }
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/login")
    @GlobalInterceptor
    public ResponseVo login(HttpServletRequest request,
                            @VerifyParam(required = true) String email,
                            @VerifyParam(required = true) String password,
                            @VerifyParam(required = true) String checkCode,
                            @VerifyParam(required = true, max = 32) String deviceId,
                            @VerifyParam(required = true) String deviceBrand) throws BusinessException {
        String redisKey = Constants.REDIS_KEY_CHECKCODE + deviceId + CHECK_CODE_TYPE_LOGIN;
        try {
            String checkCodeRedis = (String) redisUtils.get(redisKey);
            if (!checkCode.equalsIgnoreCase(checkCodeRedis)) {
                throw new BusinessException("图片验证码错误");
            }

            String token = appUserInfoService.login(email, password, getIpAddr(request), deviceId, deviceBrand);
            return getSuccessResponseVO(token);
        } finally {
            redisUtils.delete(redisKey);
        }
    }

    @RequestMapping("/autoLogin")
    @GlobalInterceptor
    public ResponseVo autoLogin(HttpServletRequest request,
                                @VerifyParam(required = true) String token,
                                @VerifyParam(required = true, max = 32) String deviceId,
                                @VerifyParam(required = true) String deviceBrand) throws BusinessException {
        String newToken = appUserInfoService.autoLogin(token, deviceId, deviceBrand, getIpAddr(request));
        return getSuccessResponseVO(newToken);
    }
}
