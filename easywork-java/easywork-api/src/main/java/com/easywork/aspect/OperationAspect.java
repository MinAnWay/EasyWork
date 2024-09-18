package com.easywork.aspect;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.component.RedisUtils;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.enums.RequestFrequencyTypeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.utils.JWTUtil;
import com.easywork.utils.StringTools;
import com.easywork.utils.VerifyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component("operationAspect")
public class OperationAspect {
    private final Logger logger = LoggerFactory.getLogger(OperationAspect.class);

    @Resource
    private JWTUtil<AppUserLoginDto> jwtUtil;

    @Resource
    private RedisUtils redisUtils;

    private static final String[] BASE_TYPE_ARRAY = new String[]{"java.lang.String", "java.lang.Integer", "java.lang.Long"};

    @Before("@annotation(com.easywork.annotation.GlobalInterceptor)")
    public void interceptorDo(JoinPoint point) throws BusinessException {
        Object target = point.getTarget();
        Object[] arguments = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
        if (interceptor == null) {
            return;
        }
        /**
         * 登录校验
         * */
        if (interceptor.checkLogin()) {
            checkLogin();
        }
        /**
         * 校验参数
         * */
        if (interceptor.checkParams()) {
            validateParams(method, arguments);
        }

        /**
         * 频次校验
         * */
        if (interceptor.frequencyType() != RequestFrequencyTypeEnum.NO_LIMIT && interceptor.requestFrequencyThreshold() != 0) {
            String fullMethodName = target.getClass().getName() + "." + method.getName();
            checkRequestFrequency(fullMethodName, interceptor.frequencyType(), interceptor.requestFrequencyThreshold());
        }

    }

    void checkRequestFrequency(String fullMethodName, RequestFrequencyTypeEnum frequencyType, Integer threshold) throws BusinessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = getIpAddr(request);
        ip = ip.replace(":", "");
        String redisKey = fullMethodName + ip;
        Integer curCount = (Integer) redisUtils.get(redisKey);
        if (curCount != null && curCount > threshold - 1) {
            throw new BusinessException(ResponseCodeEnum.CODE_903);
        }
        redisUtils.increment(redisKey, 1, frequencyType.getSeconds());
    }

    void checkLogin() throws BusinessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        AppUserLoginDto userLoginDto = jwtUtil.getTokenData(Constants.JWT_KEY_LOGIN_TOKEN, token, AppUserLoginDto.class);
        if (userLoginDto == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }

    }


    private void validateParams(Method method, Object[] arguments) throws BusinessException {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object value = arguments[i];
            VerifyParam verifyParam = parameter.getAnnotation(VerifyParam.class);
            if (verifyParam == null) {
                continue;
            }
            String paramTypeName = parameter.getParameterizedType().getTypeName();
            if (ArrayUtils.contains(BASE_TYPE_ARRAY, paramTypeName)) {
                checkValue(value, verifyParam);
            } else {
                checkObjValue(parameter, value);
            }
        }
    }

    private void checkObjValue(Parameter parameter, Object value) throws BusinessException {
        try {
            String typeName = parameter.getParameterizedType().getTypeName();
            Class classz = Class.forName(typeName);

            Field[] fields = classz.getDeclaredFields();
            for (Field field : fields) {
                VerifyParam fieldVerifyParam = field.getAnnotation(VerifyParam.class);
                if (fieldVerifyParam == null) {
                    continue;
                }
                field.setAccessible(true);
                Object resultValue = field.get(value);
                checkValue(resultValue, fieldVerifyParam);
            }
        } catch (Exception e) {
            logger.error("校验参数失败", e);
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }

    private void checkValue(Object value, VerifyParam verifyParam) throws BusinessException {
        Boolean isEmpty = value == null || StringTools.isEmpty(value.toString());
        Integer length = value == null ? 0 : value.toString().length();

        /**
         * 校验空
         * */
        if (isEmpty && verifyParam.required()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        /**
         * 校验长度
         * */
        if (!isEmpty && (verifyParam.max() != -1 && verifyParam.max() < length || verifyParam.min() != -1 && verifyParam.min() > length)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        /**
         * 校验正则
         * */
        if (!isEmpty && !StringTools.isEmpty(verifyParam.regex().getRegex()) && !VerifyUtils.verify(verifyParam.regex(), String.valueOf(value))) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
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
}
