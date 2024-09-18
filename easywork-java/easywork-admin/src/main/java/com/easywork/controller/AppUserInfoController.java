package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.query.AppDeviceQuery;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.AppUserInfo;
import com.easywork.entity.query.AppUserInfoQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.enums.UserStatusEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppDeviceService;
import com.easywork.service.AppUserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:用户信息Controller
 * @author:AndrewWay
 * @date:2024/08/07
 */
@RestController("appUserInfoController")
@RequestMapping("/appUser")
public class AppUserInfoController extends ABaseController {

    @Resource
    private AppUserInfoService appUserInfoService;

    @Resource
    private AppDeviceService appDeviceService;

    @RequestMapping("/loadDeviceList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_USER_DEVICE)
    public ResponseVo loadDeviceList(AppDeviceQuery query) {
        query.setOrderBy("create_time desc");
        return getSuccessResponseVO(appDeviceService.findListByPage(query));
    }

    @RequestMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_USER_LIST)
    public ResponseVo loadDataList(AppUserInfoQuery query) {
        query.setOrderBy("join_time desc");
        return getSuccessResponseVO(appUserInfoService.findListByPage(query));
    }

    @RequestMapping("/updateStatus")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_USER_EDIT)
    public ResponseVo updateStatus(@VerifyParam(required = true) String userId,
                                   @VerifyParam(required = true) Integer status) throws BusinessException {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setStatus(status);
        appUserInfoService.updateAppUserInfoByUserId(appUserInfo, userId);
        return getSuccessResponseVO(null);
    }
}