package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.SessionUserAdminDto;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.ShareInfo;
import com.easywork.entity.query.ShareInfoQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.enums.PostStatusEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.ShareInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description:文章分享Controller
 * @author:AndrewWay
 * @date:2024/08/03
 */
@RestController("shareInfoController")
@RequestMapping("/shareInfo")
public class ShareInfoController extends ABaseController {

    @Resource
    private ShareInfoService shareInfoService;

    @RequestMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_LIST)
    public ResponseVo loadDataList(ShareInfoQuery query) {
        query.setOrderBy("share_id desc");
        query.setQueryTextContent(true);
        return getSuccessResponseVO(shareInfoService.findListByPage(query));
    }

    @RequestMapping("/saveShareInfo")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_EDIT)
    public ResponseVo saveShareInfo(HttpSession session, Integer shareId,
                                    @VerifyParam(required = true) String title,
                                    @VerifyParam(required = true) Integer coverType,
                                    String coverPath,
                                    @VerifyParam(required = true) String content) throws BusinessException {
        ShareInfo bean = new ShareInfo();
        bean.setShareId(shareId);
        bean.setTitle(title);
        bean.setCoverType(coverType);
        bean.setCoverPath(coverPath);
        bean.setContent(content);
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        bean.setCreateUserId(String.valueOf(userAdminDto.getUserId()));
        bean.setCreateUserName(userAdminDto.getUserName());
        shareInfoService.saveShare(bean, userAdminDto.getSuperAdmin());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delShare")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_DEL)
    public ResponseVo delShare(HttpSession session, @VerifyParam(required = true) String shareIds) throws BusinessException {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        shareInfoService.delShareBatch(shareIds, userAdminDto.getSuperAdmin() ? null : userAdminDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delShareBatch")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_DEL_BATCH)
    public ResponseVo delShareBatch(@VerifyParam(required = true) String shareIds) throws BusinessException {
        shareInfoService.delShareBatch(shareIds, null);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/postShare")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_POST)
    public ResponseVo postShare(@VerifyParam(required = true) String shareIds) {
        updateStatus(shareIds, PostStatusEnum.POST.getStatus());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelPostShare")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_POST)
    public ResponseVo cancelPostShare(@VerifyParam(required = true) String shareIds) {
        updateStatus(shareIds, PostStatusEnum.NO_POST.getStatus());
        return getSuccessResponseVO(null);
    }

    private void updateStatus(String shareIds, Integer status) {
        ShareInfoQuery params = new ShareInfoQuery();
        params.setShareIds(shareIds.split(","));
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setStatus(status);
        shareInfoService.updateByParam(shareInfo, params);
    }

    @RequestMapping("/showShareDetailNext")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_LIST)
    public ResponseVo showShareDetailNext(ShareInfoQuery query, Integer nextType,
                                          @VerifyParam(required = true) Integer currentId) throws BusinessException {
        ShareInfo shareInfo = shareInfoService.showShareDetailNext(query, nextType, currentId, false);
        return getSuccessResponseVO(shareInfo);
    }
}