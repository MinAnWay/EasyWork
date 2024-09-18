package com.easywork.aspect;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.SessionUserAdminDto;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@Aspect
@Component("operationAspect")
public class OperationAspect {
    private final Logger logger = LoggerFactory.getLogger(OperationAspect.class);

    private static final String[] BASE_TYPE_ARRAY = new String[]{"java.lang.String", "java.lang.Integer", "java.lang.Long"};

    @Before("@annotation(com.easywork.annotation.GlobalInterceptor)")
    public void interceptorDo(JoinPoint point) throws BusinessException {
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
         * 校验权限
         * */
        if (interceptor.permissionCode() != null && interceptor.permissionCode() !=  PermissionCodeEnum.NO_PERMISSION) {
            checkPermission(interceptor.permissionCode());
        }
        if (interceptor.checkParams()) {
            validateParams(method, arguments);
        }
    }

    void checkLogin() throws BusinessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) request.getSession().getAttribute(Constants.SESSION_KEY);
        if (sessionUserAdminDto == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
    }

    void checkPermission(PermissionCodeEnum permissionCodeEnum) throws BusinessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) request.getSession().getAttribute(Constants.SESSION_KEY);
        List<String> permissionCodeList = sessionUserAdminDto.getPermissionCodeList();
        if (!permissionCodeList.contains(permissionCodeEnum.getCode())) {
            throw new BusinessException(ResponseCodeEnum.CODE_902);
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
        boolean isEmpty = value == null || StringTools.isEmpty(value.toString());
        int length = value == null ? 0 : value.toString().length();

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
}
