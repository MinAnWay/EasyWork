package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.config.AppConfig;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.entity.po.*;
import com.easywork.entity.query.*;
import com.easywork.entity.vo.ExamQuestionVo;
import com.easywork.entity.vo.PaginationResultVo;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.vo.app.AppUserInfoVo;
import com.easywork.enums.AnswerResultEnum;
import com.easywork.enums.CollectTypeEnum;
import com.easywork.enums.RequestFrequencyTypeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.*;
import com.easywork.utils.CopyTools;
import com.easywork.utils.ScaleFilter;
import com.easywork.utils.StringTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/12 00:28:43
 **/
@RestController("myController")
@RequestMapping("/my")
public class MyController extends ABaseController {
    @Resource
    private AppUserCollectService appUserCollectService;

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private AppExamService appExamService;

    @Resource
    private AppExamQuestionService appExamQuestionService;

    @Resource
    private ShareInfoService shareInfoService;

    @Resource
    private AppConfig appConfig;

    @Resource
    private AppUserInfoService appUserInfoService;

    @Resource
    private AppFeedbackService appFeedbackService;

    @RequestMapping("/getUserInfo")
    @GlobalInterceptor
    public ResponseVo getUserInfo(@RequestHeader(value = "token", required = false) String token) throws BusinessException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        if (null == userLoginDto) {
            return getSuccessResponseVO(null);
        }
        AppUserInfo appUserInfo = appUserInfoService.getAppUserInfoByUserId(userLoginDto.getUserId());
        if (appUserInfo == null) {
            return getSuccessResponseVO(null);
        }

        return getSuccessResponseVO(CopyTools.copy(appUserInfo, AppUserInfoVo.class));
    }

    @RequestMapping("/loadCollect")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo loadCollect(@RequestHeader(value = "token", required = false) String token,
                                  @VerifyParam(required = true) Integer collectType) throws BusinessException {
        CollectTypeEnum collectTypeEnum = CollectTypeEnum.getByType(collectType);
        if (collectTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        AppUserCollectQuery query = new AppUserCollectQuery();
        query.setCollectType(collectType);
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        query.setUserId(userLoginDto.getUserId());
        query.setOrderBy("create_id desc");
        PaginationResultVo resultVo = appUserCollectService.findListByPage(query);
        List<AppUserCollect> appUserCollectList = resultVo.getList();
        List<String> objectIdList = appUserCollectList.stream().map(item -> item.getObjectId()).collect(Collectors.toList());
        if (objectIdList.isEmpty()) {
            return getSuccessResponseVO(resultVo);
        }

        Map<Integer, AppUserCollect> objectIdMap = appUserCollectList.stream().
                collect(Collectors.toMap(item -> Integer.parseInt(item.getObjectId()), Function.identity(), (data1, data2) -> data2));
        switch (collectTypeEnum) {
            case SHARE:
                ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
                shareInfoQuery.setShareIds(objectIdList.toArray(new String[objectIdList.size()]));
                shareInfoQuery.setOrderBy("field(share_id," + StringUtils.join(objectIdList, ",") + ")");
                List<ShareInfo> shareInfoList = shareInfoService.findListByParam(shareInfoQuery);
                for (ShareInfo item : shareInfoList) {
                    AppUserCollect collect = objectIdMap.get(item.getShareId());
                    item.setCollectId(collect.getCollectId());
                }
                resultVo.setList(shareInfoList);
                break;
            case QUESTION:
                QuestionInfoQuery questionInfoQuery = new QuestionInfoQuery();
                questionInfoQuery.setQuestionIds(objectIdList.toArray(new String[objectIdList.size()]));
                questionInfoQuery.setOrderBy("field(question_id," + StringUtils.join(objectIdList, ",") + ")");
                List<QuestionInfo> questionInfoList = questionInfoService.findListByParam(questionInfoQuery);
                for (QuestionInfo item : questionInfoList) {
                    AppUserCollect collect = objectIdMap.get(item.getQuestionId());
                    item.setCollectId(collect.getCollectId());
                }
                resultVo.setList(questionInfoList);
                break;
            case EXAM:
                AppExamQuestionQuery appExamQuestionQuery = new AppExamQuestionQuery();
                appExamQuestionQuery.setUserId(userLoginDto.getUserId());
                appExamQuestionQuery.setShowUserAnswer(true);
                appExamQuestionQuery.setQuestionIds(objectIdList);
                List<ExamQuestionVo> examQuestionVoList = appExamService.getAppExamQuestion(appExamQuestionQuery);
                resultVo.setList(examQuestionVoList);
        }
        return getSuccessResponseVO(resultVo);
    }

    @RequestMapping("/getCollectNext")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo getCollectNext(@RequestHeader(value = "token", required = false) String token,
                                     @VerifyParam(required = true) Integer currentId,
                                     @VerifyParam(required = true) Integer collectType,
                                     Integer nextType) throws BusinessException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        CollectTypeEnum collectTypeEnum = CollectTypeEnum.getByType(collectType);
        if (collectTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        AppUserCollectQuery query = new AppUserCollectQuery();
        query.setUserId(userLoginDto.getUserId());
        query.setCollectType(collectType);
        AppUserCollect appUserCollect = appUserCollectService.showDetailNext(query, nextType, currentId);
        Integer objectId = Integer.parseInt(appUserCollect.getObjectId());
        switch (collectTypeEnum) {
            case SHARE:
                ShareInfo shareInfo = shareInfoService.getShareInfoByShareId(objectId);
                shareInfo.setCollectId(appUserCollect.getCollectId());
                return getSuccessResponseVO(shareInfo);
            case QUESTION:
                QuestionInfo questionInfo = questionInfoService.getQuestionInfoByQuestionId(objectId);
                questionInfo.setCollectId(appUserCollect.getCollectId());
                return getSuccessResponseVO(questionInfo);
        }
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadMyExam")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo loadMyExam(@RequestHeader(value = "token", required = false) String token,
                                 Integer pageNo) throws BusinessException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        AppExamQuery appExamQuery = new AppExamQuery();
        appExamQuery.setPageNo(pageNo);
        appExamQuery.setUserId(userLoginDto.getUserId());
        appExamQuery.setOrderBy("exam_id desc");
        PaginationResultVo resultVo = appExamService.findListByPage(appExamQuery);
        return getSuccessResponseVO(resultVo);
    }

    @RequestMapping("/loadWrongQuestion")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo loadWrongQuestion(@RequestHeader(value = "token", required = false) String token,
                                        Integer pageNo) throws BusinessException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        AppExamQuestionQuery appExamQuestionQuery = new AppExamQuestionQuery();
        appExamQuestionQuery.setPageNo(pageNo);
        appExamQuestionQuery.setOrderBy("exam_id desc");
        appExamQuestionQuery.setUserId(userLoginDto.getUserId());
        appExamQuestionQuery.setAnswerResult(AnswerResultEnum.WRONG.getResult());
        PaginationResultVo resultVo = appExamQuestionService.findListByPage(appExamQuestionQuery);

        List<AppExamQuestion> appExamQuestionList = resultVo.getList();
        List<String> questionIds = appExamQuestionList.stream().map(item -> item.getQuestionId().toString()).collect(Collectors.toList());
        if (questionIds.isEmpty()) {
            return getSuccessResponseVO(resultVo);
        }
        appExamQuestionQuery = new AppExamQuestionQuery();
        appExamQuestionQuery.setShowUserAnswer(true);
        appExamQuestionQuery.setQuestionIds(questionIds);
        appExamQuestionQuery.setAnswerResult(AnswerResultEnum.WRONG.getResult());
        List<ExamQuestionVo> examQuestionVoList = appExamService.getAppExamQuestion(appExamQuestionQuery);
        for (ExamQuestionVo item : examQuestionVoList) {
            item.setQuestion(resetContentImg(item.getQuestion()));
            item.setAnswerAnalysis(resetContentImg(item.getAnswerAnalysis()));
        }
        resultVo.setList(examQuestionVoList);
        return getSuccessResponseVO(resultVo);
    }

    @RequestMapping("/uploadAvatar")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo uploadAvatar(@RequestHeader(value = "token", required = false) String token,
                                   MultipartFile file) throws BusinessException, IOException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        String folderName = appConfig.getProjectFolder() + Constants.FOLDER_AVATAR;
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String avatarName = userLoginDto.getUserId() + StringTools.getFileSuffix(file.getOriginalFilename());
        File avatarFileName = new File(folder.getPath() + "/" + avatarName);
        file.transferTo(avatarFileName);
        ScaleFilter.createThumbnail(avatarFileName, Constants.LENGTH_70, Constants.LENGTH_70, avatarFileName);
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setAvatar(Constants.FOLDER_AVATAR + avatarName);
        appUserInfoService.updateAppUserInfoByUserId(appUserInfo, userLoginDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updateUserInfo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo updateUserInfo(@RequestHeader(value = "token", required = false) String token,
                                     @VerifyParam(required = true) Integer sex,
                                     @VerifyParam(required = true) String password) throws BusinessException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setSex(sex);
        if (!StringTools.isEmpty(password)) {
            appUserInfo.setPassword(StringTools.encodeMd5(password));
        }
        appUserInfoService.updateAppUserInfoByUserId(appUserInfo, userLoginDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadFeedback")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo loadFeedback(@RequestHeader(value = "token", required = false) String token,
                                   Integer pageNo) throws BusinessException {
        AppFeedbackQuery appFeedbackQuery = new AppFeedbackQuery();
        appFeedbackQuery.setOrderBy("feedback_id desc");
        appFeedbackQuery.setUserId(getAppUserLoginInfoFromToken(token).getUserId());
        appFeedbackQuery.setPageNo(pageNo);
        appFeedbackQuery.setPFeedbackId(0);
        PaginationResultVo resultVo = appFeedbackService.findListByPage(appFeedbackQuery);
        return getSuccessResponseVO(resultVo);
    }

    @RequestMapping("/loadFeedbackReply")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo loadFeedbackReply(@RequestHeader(value = "token", required = false) String token,
                                        @VerifyParam(required = true) Integer pFeedbackId) throws BusinessException {
        AppFeedbackQuery appFeedbackQuery = new AppFeedbackQuery();
        appFeedbackQuery.setOrderBy("feedback_id desc");
        appFeedbackQuery.setUserId(getAppUserLoginInfoFromToken(token).getUserId());
        appFeedbackQuery.setPFeedbackId(pFeedbackId);
        List<AppFeedback> feedbackList = appFeedbackService.findListByParam(appFeedbackQuery);
        return getSuccessResponseVO(feedbackList);
    }

    @RequestMapping("/sendFeedback")
    @GlobalInterceptor(checkLogin = true, frequencyType = RequestFrequencyTypeEnum.DAY, requestFrequencyThreshold = 10)
    public ResponseVo sendFeedback(@RequestHeader(value = "token", required = false) String token,
                                   @VerifyParam(required = true) String content,
                                   @VerifyParam(required = true) Integer pFeedbackId) throws BusinessException {
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        AppFeedback appFeedback = new AppFeedback();
        appFeedback.setUserId(userLoginDto.getUserId());
        appFeedback.setNickName(userLoginDto.getNickName());
        appFeedback.setContent(content);
        appFeedback.setPFeedbackId(pFeedbackId);
        appFeedbackService.saveFeedBack4Client(appFeedback);
        return getSuccessResponseVO(appFeedback);

    }
}
