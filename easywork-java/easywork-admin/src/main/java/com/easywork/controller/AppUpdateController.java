package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.AppUpdate;
import com.easywork.entity.query.AppUpdateQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppUpdateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description:app发布Controller
 * @author:AndrewWay
 * @date:2024/08/07
 */
@RestController("appUpdateController")
@RequestMapping("/app")
public class AppUpdateController extends ABaseController {
    @Resource
    private AppUpdateService appUpdateService;

    @RequestMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_LIST)
    public ResponseVo loadDataList(AppUpdateQuery query) {
        query.setOrderBy("id desc");
        return getSuccessResponseVO(appUpdateService.findListByPage(query));
    }

    @RequestMapping("/saveUpdate")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_EDIT)
    public ResponseVo saveUpdate(Integer id, @VerifyParam(required = true) String version,
                                 @VerifyParam(required = true) String updateDesc,
                                 @VerifyParam(required = true) Integer updateType,
                                 MultipartFile file) throws BusinessException {
        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setId(id);
        appUpdate.setVersion(version);
        appUpdate.setUpdateDesc(updateDesc);
        appUpdate.setUpdateType(updateType);
        appUpdateService.saveUpdate(appUpdate, file);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delUpdate")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_EDIT)
    public ResponseVo delUpdate(@VerifyParam(required = true) Integer id) throws BusinessException {
        appUpdateService.deleteAppUpdateById(id);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/postUpdate")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_POST)
    public ResponseVo postUpdate(@VerifyParam(required = true) Integer id,
                                 @VerifyParam(required = true) Integer status,
                                 String grayscaleDevice) throws BusinessException {
        appUpdateService.postUpdate(id, status, grayscaleDevice);
        return getSuccessResponseVO(null);
    }
}