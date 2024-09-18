package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.config.AppConfig;
import com.easywork.entity.po.SysAccount;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.query.AccountQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.enums.UserStatusEnum;
import com.easywork.enums.VerifyRegexEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.SysAccountService;
import com.easywork.utils.StringTools;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:账号信息Controller
 * @author:AndrewWay
 * @date:2024/07/31
 */
@RestController("sysAccountController")
@RequestMapping("/settings")
public class SysAccountController extends ABaseController {

    @Resource
    private SysAccountService sysAccountService;

    @Resource
    private AppConfig appConfig;

    @RequestMapping("/loadAccountList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_LIST)
    public ResponseVo loadAccountList(AccountQuery query) {
        query.setOrderBy("create_time desc");
        query.setQueryRoles(true);
        return getSuccessResponseVO(sysAccountService.findListByPage(query));
    }

    @RequestMapping("/saveAccount")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_EDIT)
    public ResponseVo saveAccount(@VerifyParam SysAccount sysAccount) throws BusinessException {
        sysAccountService.saveSysAccount(sysAccount);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updatePassword")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_UPDATE_PASSWORD)
    public ResponseVo updatePassword(@VerifyParam Integer userId,
                                     @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD) String password) throws BusinessException {
        SysAccount updateInfo = new SysAccount();
        updateInfo.setPassword(StringTools.encodeMd5(password));
        sysAccountService.updateAccountByUserId(updateInfo, userId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updateStatus")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_OP_STATUS)
    public ResponseVo updateStatus(@VerifyParam Integer userId,
                                     @VerifyParam(required = true) Integer status) throws BusinessException {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SysAccount updateInfo = new SysAccount();
        updateInfo.setStatus(status);
        sysAccountService.updateAccountByUserId(updateInfo, userId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delAccount")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_EDIT)
    public ResponseVo delAccount(@VerifyParam Integer userId) throws BusinessException {
       SysAccount sysAccount = this.sysAccountService.getAccountByUserId(userId);
       if (!StringTools.isEmpty(appConfig.getSuperAdminPhones())&& ArrayUtils.contains(appConfig.getSuperAdminPhones().split(","), sysAccount.getPhone())){
           throw new BusinessException("系统超级管理员不允许删除");
       }
        sysAccountService.deleteAccountByUserId(userId);
        return getSuccessResponseVO(null);
    }
}