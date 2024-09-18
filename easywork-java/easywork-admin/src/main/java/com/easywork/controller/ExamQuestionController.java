package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.ImportErrorItem;
import com.easywork.entity.dto.SessionUserAdminDto;
import com.easywork.entity.po.ExamQuestionItem;
import com.easywork.entity.query.ExamQuestionItemQuery;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.ExamQuestion;
import com.easywork.entity.query.ExamQuestionQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.enums.PostStatusEnum;
import com.easywork.enums.QuestionTypeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.ExamQuestionItemService;
import com.easywork.service.ExamQuestionService;
import com.easywork.utils.JsonUtils;
import com.easywork.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:考试题目Controller
 * @author:AndrewWay
 * @date:2024/08/03
 */
@RestController("examQuestionController")
@RequestMapping("/examQuestion")
public class ExamQuestionController extends ABaseController {

    @Resource
    private ExamQuestionService examQuestionService;

    @Resource
    private ExamQuestionItemService examQuestionItemService;

    @RequestMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_LIST)
    public ResponseVo loadDataList(ExamQuestionQuery query) {
        query.setOrderBy("question_id desc");
        query.setQueryAnswer(true);
        return getSuccessResponseVO(examQuestionService.findListByPage(query));
    }

    @RequestMapping("/saveExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public ResponseVo saveExamQuestion(HttpSession session, @VerifyParam(required = true) ExamQuestion examQuestion,
                                       String questionItemListJson) throws BusinessException {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        examQuestion.setCreateUserId(String.valueOf(userAdminDto.getUserId()));
        examQuestion.setCreateUserName(userAdminDto.getUserName());
        List<ExamQuestionItem> examQuestionItemList = new ArrayList<>();
        if (!QuestionTypeEnum.TRUE_FALSE.getType().equals(examQuestion.getQuestionType())) {
            if (StringTools.isEmpty(questionItemListJson)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            examQuestionItemList = JsonUtils.convertJsonArray2List(questionItemListJson, ExamQuestionItem.class);
        }
        examQuestionService.saveExamQuestion(examQuestion, examQuestionItemList, userAdminDto.getSuperAdmin());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadQuestionItem")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public ResponseVo loadQuestionItem(@VerifyParam(required = true) Integer questionId) {
        ExamQuestionItemQuery itemQuery = new ExamQuestionItemQuery();
        itemQuery.setQuestionId(questionId);
        itemQuery.setOrderBy("sort asc");
        return getSuccessResponseVO(examQuestionItemService.findListByParam(itemQuery));
    }

    @RequestMapping("/delExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public ResponseVo delExamQuestion(HttpSession session, @VerifyParam(required = true) Integer questionId) throws BusinessException {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        examQuestionService.delExamQuestionBatch(String.valueOf(questionId), userAdminDto.getSuperAdmin() ? null : userAdminDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delExamQuestionBatch")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL_BATCH)
    public ResponseVo delExamQuestionBatch(@VerifyParam(required = true) String questionIds) throws BusinessException {
        examQuestionService.delExamQuestionBatch(questionIds, null);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/postExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_POST)
    public ResponseVo postExamQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.POST.getStatus());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelPostExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_POST)
    public ResponseVo cancelPostExamQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.NO_POST.getStatus());
        return getSuccessResponseVO(null);
    }


    private void updateStatus(String questionIds, Integer status) {
        ExamQuestionQuery query = new ExamQuestionQuery();
        query.setQuestionIds(questionIds.split(","));
        ExamQuestion question = new ExamQuestion();
        question.setStatus(status);
        examQuestionService.updateByParam(question, query);
    }

    @RequestMapping("/showExamQuestionDetailNext")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_LIST)
    public ResponseVo showExamQuestionDetailNext(ExamQuestionQuery query,
                                                 Integer nextType,
                                                 @VerifyParam(required = true) Integer currentId) throws BusinessException {
        ExamQuestion examQuestion = examQuestionService.showDetailNext(query, nextType, currentId);
        return getSuccessResponseVO(examQuestion);
    }

    @RequestMapping("/importExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_IMPORT)
    public ResponseVo importExamQuestion(HttpSession session, MultipartFile file) throws BusinessException {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        List<ImportErrorItem> errorItemList = examQuestionService.importExamQuestion(file, userAdminDto);
        return getSuccessResponseVO(errorItemList);
    }
}