package com.easywork.controller;

import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.entity.po.AppUserCollect;
import com.easywork.entity.po.ShareInfo;
import com.easywork.entity.query.ShareInfoQuery;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.CollectTypeEnum;
import com.easywork.enums.PostStatusEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppUserCollectService;
import com.easywork.service.ShareInfoService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 01:46:15
 **/
@RestController("shareController")
@RequestMapping("/share")
public class ShareController extends ABaseController {
    @Resource
    private AppUserCollectService appUserCollectService;
    @Resource
    private ShareInfoService shareInfoService;

    @RequestMapping("/loadShareList")
    public ResponseVo loadShareList(Integer pageNo) {
        ShareInfoQuery query = new ShareInfoQuery();
        query.setPageNo(pageNo);
        query.setOrderBy("share_id desc");
        query.setQueryTextContent(false);
        query.setStatus(PostStatusEnum.POST.getStatus());
        return getSuccessResponseVO(shareInfoService.findListByPage(query));
    }

    @RequestMapping("/getShareDetailNext")
    public ResponseVo getShareDetailNext(@RequestHeader(value = "token", required = false) String token,
                                         @VerifyParam(required = true) Integer currentId, Integer nextType) throws BusinessException {
        ShareInfoQuery query = new ShareInfoQuery();
        query.setStatus(PostStatusEnum.POST.getStatus());
        ShareInfo shareInfo = shareInfoService.showShareDetailNext(query, nextType, currentId, true);
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        if (userLoginDto != null) {
            AppUserCollect appUserCollect = appUserCollectService.getAppUserCollectByUserIdAndObjectIdAndCollectType(userLoginDto.getUserId(),
                    shareInfo.getShareId().toString(), CollectTypeEnum.SHARE.getType());
            if (appUserCollect != null) {
                shareInfo.setHaveCollect(true);
            }
        }
        shareInfo.setContent(resetContentImg(shareInfo.getContent()));
        return getSuccessResponseVO(shareInfo);
    }
}
