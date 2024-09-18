package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.AppFeedback;
import com.easywork.entity.query.AppFeedbackQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppFeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:问题反馈Controller
 * @author:AndrewWay
 * @date:2024/08/07
 */
@RestController("appFeedbackController")
@RequestMapping("/app")
public class AppFeedbackController extends ABaseController {

    @Resource
    private AppFeedbackService appFeedbackService;

    @RequestMapping("/loadFeedback")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_FEEDBACK_LIST)
    public ResponseVo loadFeedback(AppFeedbackQuery query) {
        query.setOrderBy("feedback_id desc");
        query.setFeedbackId(0);
        return getSuccessResponseVO(appFeedbackService.findListByPage(query));
    }

    @RequestMapping("/loadFeedbackReply")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_FEEDBACK_REPLY)
    public ResponseVo loadFeedbackReply(@VerifyParam(required = true) Integer pFeedbackId) {
        AppFeedbackQuery query = new AppFeedbackQuery();
        query.setOrderBy("feedback_id asc");
        query.setPFeedbackId(pFeedbackId);
        return getSuccessResponseVO(appFeedbackService.findListByParam(query));
    }

    @RequestMapping("/replyFeedback")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_FEEDBACK_REPLY)
    public ResponseVo replyFeedback(@VerifyParam(required = true, max = 500) String content,
                                    @VerifyParam(required = true) Integer pFeedbackId) throws BusinessException {
        AppFeedback appFeedback = new AppFeedback();
        appFeedback.setPFeedbackId(pFeedbackId);
        appFeedback.setContent(content);
        appFeedbackService.replyFeedback(appFeedback);
        return getSuccessResponseVO(null);
    }

}