package com.easywork.controller;

import com.easywork.annotation.VerifyParam;
import com.easywork.entity.dto.AppUserLoginDto;
import com.easywork.entity.po.AppUserCollect;
import com.easywork.entity.po.QuestionInfo;
import com.easywork.entity.query.QuestionInfoQuery;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.CollectTypeEnum;
import com.easywork.enums.PostStatusEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.AppUserCollectService;
import com.easywork.service.QuestionInfoService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/10 17:12:55
 **/
@RestController
@RequestMapping("/question")
public class QuestionController extends ABaseController {
    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private AppUserCollectService appUserCollectService;

    @RequestMapping("/loadQuestion")
    public ResponseVo loadQuestion(Integer pageNo, Integer categoryId) {
        QuestionInfoQuery query = new QuestionInfoQuery();
        query.setPageNo(pageNo);
        query.setCategoryId(categoryId);
        query.setOrderBy("question_id desc");
        if (query.getCategoryId() != null && query.getCategoryId() == 0) {
            query.setCategoryId(null);
        }
        query.setQueryTextContent(false);
        query.setStatus(PostStatusEnum.POST.getStatus());
        return getSuccessResponseVO(questionInfoService.findListByPage(query));
    }

    @RequestMapping("/getQuestionDataDetail")
    public ResponseVo getQuestionDataDetail(@RequestHeader(value = "token", required = false) String token,
                                            @VerifyParam(required = true) Integer currentId, Integer nextType) throws BusinessException {
        QuestionInfoQuery query = new QuestionInfoQuery();
        query.setStatus(PostStatusEnum.POST.getStatus());
        QuestionInfo questionInfo = questionInfoService.showDetailNext(query, nextType, currentId, true);
        AppUserLoginDto userLoginDto = getAppUserLoginInfoFromToken(token);
        if (userLoginDto != null) {
            AppUserCollect appUserCollect = appUserCollectService.getAppUserCollectByUserIdAndObjectIdAndCollectType(userLoginDto.getUserId(), questionInfo.getQuestionId().toString(), CollectTypeEnum.QUESTION.getType());
            if (appUserCollect != null) {
                questionInfo.setHaveCollect(true);
            }
        }
        questionInfo.setQuestion(resetContentImg(questionInfo.getQuestion()));
        questionInfo.setAnswerAnalysis(resetContentImg(questionInfo.getAnswerAnalysis()));
        return getSuccessResponseVO(questionInfo);
    }
}
