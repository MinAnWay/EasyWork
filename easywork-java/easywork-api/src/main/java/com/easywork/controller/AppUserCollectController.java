package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppUserCollectService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/10 18:01:57
 **/
@RestController
@RequestMapping("/appUserCollect")
public class AppUserCollectController extends ABaseController {

    @Resource
    private AppUserCollectService appUserCollectService;

    @RequestMapping("/addCollect")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo addCollect(@RequestHeader(value = "token", required = false) String token,
                                 @VerifyParam(required = true) String objectId,
                                 @VerifyParam(required = true) Integer collectType) throws BusinessException {
        AppUserLoginDto loginDto = getAppUserLoginInfoFromToken(token);
        appUserCollectService.saveCollect(loginDto.getUserId(), objectId, collectType);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelCollect")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo cancelCollect(@RequestHeader(value = "token", required = false) String token,
                                    @VerifyParam(required = true) String objectId,
                                    @VerifyParam(required = true) Integer collectType) throws BusinessException {
        AppUserLoginDto loginDto = getAppUserLoginInfoFromToken(token);
        appUserCollectService.deleteAppUserCollectByUserIdAndObjectIdAndCollectType(loginDto.getUserId(), objectId, collectType);
        return getSuccessResponseVO(null);
    }
}
