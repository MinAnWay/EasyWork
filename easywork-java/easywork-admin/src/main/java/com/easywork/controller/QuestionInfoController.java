package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.ImportErrorItem;
import com.easywork.entity.dto.SessionUserAdminDto;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.QuestionInfo;
import com.easywork.entity.query.QuestionInfoQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.enums.PostStatusEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.QuestionInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:问题Controller
 * @author:AndrewWay
 * @date:2024/08/03
 */
@RestController("questionInfoController")
@RequestMapping("/questionInfo")
public class QuestionInfoController extends ABaseController {

    @Resource
    private QuestionInfoService questionInfoService;

    @RequestMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public ResponseVo loadDataList(QuestionInfoQuery query) {
        query.setOrderBy("question_id desc");
        query.setQueryTextContent(true);
        return getSuccessResponseVO(questionInfoService.findListByPage(query));
    }

    @RequestMapping("/saveQuestionInfo")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_EDIT)
    public ResponseVo saveQuestionInfo(HttpSession session, QuestionInfo bean) throws BusinessException {
        SessionUserAdminDto sessionUserAdminDto = getUserAdminFromSession(session);
        bean.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        bean.setCreateUserName(sessionUserAdminDto.getUserName());
        questionInfoService.saveQuestion(bean, sessionUserAdminDto.getSuperAdmin());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_EDIT)
    public ResponseVo delQuestion(HttpSession session, @VerifyParam(required = true) Integer questionId) throws BusinessException {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        questionInfoService.delQuestionBatch(String.valueOf(questionId), userAdminDto.getSuperAdmin() ? null : userAdminDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delQuestionBatch")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_EDIT)
    public ResponseVo delQuestionBatch(@VerifyParam(required = true) String questionIds) throws BusinessException {
        questionInfoService.delQuestionBatch(questionIds, null);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/postQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_POST)
    public ResponseVo postQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.POST.getStatus());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelPostQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_POST)
    public ResponseVo cancelPostQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.NO_POST.getStatus());
        return getSuccessResponseVO(null);
    }

    private void updateStatus(String questionIds, Integer status) {
        QuestionInfoQuery params = new QuestionInfoQuery();
        params.setQuestionIds(questionIds.split(","));
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setStatus(status);
        questionInfoService.updateByParams(questionInfo, params);
    }

    @RequestMapping("/importQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_IMPORT)
    public ResponseVo importQuestion(HttpSession session, MultipartFile file) throws BusinessException {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        List<ImportErrorItem> errorList = questionInfoService.importQuestion(file, userAdminDto);
        return getSuccessResponseVO(errorList);
    }

    @RequestMapping("/showQuestionDetailNext")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public ResponseVo showQuestionDetailNext(QuestionInfoQuery query,
                                             Integer nextType,
                                             @VerifyParam(required = true) Integer currentId) throws BusinessException {
        QuestionInfo questionInfo = questionInfoService.showDetailNext(query, nextType, currentId, false);
        return getSuccessResponseVO(questionInfo);
    }
}