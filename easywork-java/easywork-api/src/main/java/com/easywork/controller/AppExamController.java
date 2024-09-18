package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.AppExamPostDto;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.entity.query.AppExamQuery;
import com.easywork.entity.query.AppExamQuestionQuery;
import com.easywork.entity.vo.ExamQuestionVo;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.AppExam;
import com.easywork.entity.vo.app.AppExamVo;
import com.easywork.enums.AppExamStatusEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppExamService;
import com.easywork.utils.CopyTools;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:用户在线考试Controller
 * @author:AndrewWay
 * @date:2024/08/11
 */
@RestController
@RequestMapping("/appExam")
public class AppExamController extends ABaseController {

    @Resource
    private AppExamService appExamService;

    @RequestMapping("loadNoFinishExam")
    @GlobalInterceptor
    public ResponseVo loadNoFinishExam(@RequestHeader(value = "token", required = false) String token) throws BusinessException {
        AppUserLoginDto appDto = getAppUserLoginInfoFromToken(token);
        if (appDto == null) {
            return getSuccessResponseVO(null);
        }
        AppExamQuery appExamQuery = new AppExamQuery();
        appExamQuery.setStatus(AppExamStatusEnum.INIT.getStatus());
        appExamQuery.setUserId(appDto.getUserId());
        appExamQuery.setOrderBy("exam_id desc");
        List<AppExam> appExamList = appExamService.findListByParam(appExamQuery);
        return getSuccessResponseVO(appExamList);
    }

    @RequestMapping("createExam")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo createExam(@RequestHeader(value = "token", required = false) String token,
                                 @VerifyParam(required = true) String categoryIds) throws BusinessException {
        AppUserLoginDto appDto = getAppUserLoginInfoFromToken(token);
        return getSuccessResponseVO(appExamService.createExam(categoryIds, appDto));
    }

    @RequestMapping("/getExamQuestion")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo getExamQuestion(@RequestHeader(value = "token", required = false) String token,
                                      @VerifyParam(required = true) Integer examId) throws BusinessException {
        AppUserLoginDto appDto = getAppUserLoginInfoFromToken(token);
        AppExam appExam = checkAppExam(appDto, examId);

        boolean showAnswer = false;
        if (AppExamStatusEnum.FINISH.getStatus().equals(appExam.getStatus())) {
            showAnswer = true;
        }
        AppExamVo appExamVo = CopyTools.copy(appExam, AppExamVo.class);
        AppExamQuestionQuery appExamQuestionQuery = new AppExamQuestionQuery();
        appExamQuestionQuery.setExamId(examId);
        appExamQuestionQuery.setUserId(appDto.getUserId());
        appExamQuestionQuery.setShowUserAnswer(showAnswer);
        List<ExamQuestionVo> examQuestionVoList = appExamService.getAppExamQuestion(appExamQuestionQuery);

        for (ExamQuestionVo item : examQuestionVoList) {
            item.setQuestion(resetContentImg(item.getQuestion()));
            item.setAnswerAnalysis(resetContentImg(item.getAnswerAnalysis()));
        }
        appExamVo.setExamQuestionList(examQuestionVoList);
        return getSuccessResponseVO(appExamVo);
    }

    private AppExam checkAppExam(AppUserLoginDto appDto, Integer examId) throws BusinessException {
        AppExam appExam = appExamService.getAppExamByExamId(examId);
        if (null == appExam || !appExam.getUserId().equals(appDto.getUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        return appExam;
    }

    @RequestMapping("/startExam")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo startExam(@RequestHeader(value = "token", required = false) String token,
                                @VerifyParam(required = true) Integer examId) throws BusinessException {
        AppUserLoginDto appDto = getAppUserLoginInfoFromToken(token);
        checkAppExam(appDto, examId);
        Date curDate = new Date();
        AppExam updateInfo = new AppExam();
        updateInfo.setStartTime(curDate);
        AppExamQuery appExamQuery = new AppExamQuery();
        appExamQuery.setExamId(examId);
        appExamQuery.setUserId(appDto.getUserId());
        appExamService.updateByParam(updateInfo, appExamQuery);
        return getSuccessResponseVO(curDate);
    }

    @RequestMapping("/postExam")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo postExam(@RequestHeader(value = "token", required = false) String token,
                               @RequestBody AppExamPostDto appExamPostDto) throws BusinessException {
        AppUserLoginDto appDto = getAppUserLoginInfoFromToken(token);
        AppExam appExam = appExamService.postExam(appDto, appExamPostDto);
        return getSuccessResponseVO(appExam);
    }

    @RequestMapping("/delExam")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo delExam(@RequestHeader(value = "token", required = false) String token,
                              @VerifyParam(required = true) Integer examId) throws BusinessException {
        AppUserLoginDto appDto = getAppUserLoginInfoFromToken(token);
        checkAppExam(appDto, examId);
        appExamService.delExam4Api(appDto.getUserId(), examId);
        return getSuccessResponseVO(null);
    }
}