package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.po.ExamQuestion;
import com.easywork.entity.po.QuestionInfo;
import com.easywork.entity.po.ShareInfo;
import com.easywork.entity.query.ExamQuestionQuery;
import com.easywork.entity.query.QuestionInfoQuery;
import com.easywork.entity.query.ShareInfoQuery;
import com.easywork.entity.vo.PaginationResultVo;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.PostStatusEnum;
import com.easywork.enums.RequestFrequencyTypeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.enums.SearchTypeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.ExamQuestionService;
import com.easywork.service.QuestionInfoService;
import com.easywork.service.ShareInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/11 01:21:26
 **/
@RestController("searchController")
@RequestMapping("/search")
public class SearchController extends ABaseController {

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private ExamQuestionService examQuestionService;

    @Resource
    private ShareInfoService shareInfoService;

    @RequestMapping("/search")
    @GlobalInterceptor(frequencyType = RequestFrequencyTypeEnum.MINUTE, requestFrequencyThreshold = 20)
    public ResponseVo search(@VerifyParam(required = true, min = 1) String keyword,
                             @VerifyParam(required = true) Integer type,
                             Integer pageNo) throws BusinessException {
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getByType(type);
        if (searchTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        switch (searchTypeEnum) {
            case QUESTION:
                QuestionInfoQuery query = new QuestionInfoQuery();
                query.setPageNo(pageNo);
                query.setTitleFuzzy(keyword);
                query.setOrderBy("question_id desc");
                query.setQueryTextContent(false);
                query.setStatus(PostStatusEnum.POST.getStatus());
                PaginationResultVo<QuestionInfo> questionInfo = questionInfoService.findListByPage(query);
                for (QuestionInfo item : questionInfo.getList()) {
                    item.setQuestion(resetContentImg(item.getQuestion()));
                    item.setAnswerAnalysis(resetContentImg(item.getAnswerAnalysis()));
                }
                return getSuccessResponseVO(questionInfo);
            case EXAM_QUESTION:
                ExamQuestionQuery examQuestionQuery = new ExamQuestionQuery();
                examQuestionQuery.setPageNo(pageNo);
                examQuestionQuery.setTitleFuzzy(keyword);
                examQuestionQuery.setOrderBy("question_id desc");
                examQuestionQuery.setStatus(PostStatusEnum.POST.getStatus());
                examQuestionQuery.setQueryAnswer(true);
                examQuestionQuery.setQueryQuestionItem(true);
                PaginationResultVo<ExamQuestion> examQuestionVo = examQuestionService.findListByPage(examQuestionQuery);
                for (ExamQuestion item : examQuestionVo.getList()) {
                    item.setQuestion(resetContentImg(item.getQuestion()));
                    item.setAnswerAnalysis(resetContentImg(item.getAnswerAnalysis()));
                }
                return getSuccessResponseVO(examQuestionVo);
            case SHARE:
                ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
                shareInfoQuery.setTitleFuzzy(keyword);
                shareInfoQuery.setPageNo(pageNo);
                shareInfoQuery.setOrderBy("share_id desc");
                shareInfoQuery.setStatus(PostStatusEnum.POST.getStatus());
                shareInfoQuery.setQueryTextContent(false);
                PaginationResultVo<ShareInfo> shareVo = shareInfoService.findListByPage(shareInfoQuery);
                for (ShareInfo item : shareVo.getList()) {
                    item.setContent(resetContentImg(item.getContent()));
                }
                return getSuccessResponseVO(shareVo);
            default:
                throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }
}
