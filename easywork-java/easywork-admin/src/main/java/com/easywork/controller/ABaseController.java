package com.easywork.controller;

import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.SessionUserAdminDto;
import com.easywork.enums.ResponseCodeEnum;;

import com.easywork.entity.vo.ResponseVo;;import javax.servlet.http.HttpSession;

public class ABaseController {

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

    protected SessionUserAdminDto getUserAdminFromSession(HttpSession session) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        return sessionUserAdminDto;
    }
}
