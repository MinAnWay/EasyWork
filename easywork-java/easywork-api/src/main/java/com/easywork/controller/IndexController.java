package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.po.AppCarousel;
import com.easywork.entity.po.AppDevice;
import com.easywork.entity.po.ExamQuestion;
import com.easywork.entity.po.ExamQuestionItem;
import com.easywork.entity.query.AppCarouselQuery;
import com.easywork.entity.query.ExamQuestionItemQuery;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.PostStatusEnum;
import com.easywork.enums.RequestFrequencyTypeEnum;
import com.easywork.enums.ResponseCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description 首页
 *
 * @author Andrew
 * @date 2024/8/10 14:53:28
 **/
@RestController("indexController")
@RequestMapping("/index")
public class IndexController extends ABaseController {
    @Resource
    private CategoryService categoryService;

    @Resource
    private AppCarouselService appCarouselService;

    @Resource
    private ExamQuestionService examQuestionService;

    @Resource
    private ExamQuestionItemService examQuestionItemService;

    @Resource
    private AppDeviceService appDeviceService;

    @RequestMapping("/loadAllCategory")
    @GlobalInterceptor
    public ResponseVo loadAllCategory(@VerifyParam(required = true) Integer type) throws BusinessException {
        return getSuccessResponseVO(categoryService.loadAllCategoryByType(type));
    }

    @RequestMapping("/loadCarousel")
    @GlobalInterceptor
    public ResponseVo loadCarousel() {
        AppCarouselQuery query = new AppCarouselQuery();
        query.setOrderBy("sort asc");
        List<AppCarousel> appCarouselList = appCarouselService.findListByParam(query);
        return getSuccessResponseVO(appCarouselList);
    }

    @RequestMapping("/getExamQuestionById")
    @GlobalInterceptor
    public ResponseVo getExamQuestionById(@VerifyParam(required = true) Integer questionId) throws BusinessException {
        ExamQuestion examQuestion = examQuestionService.getExamQuestionByQuestionId(questionId);
        if (examQuestion == null || !PostStatusEnum.POST.getStatus().equals(examQuestion.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        ExamQuestionItemQuery itemQuery = new ExamQuestionItemQuery();
        itemQuery.setQuestionId(questionId);
        itemQuery.setOrderBy("sort asc");
        List<ExamQuestionItem> questionItemList = examQuestionItemService.findListByParam(itemQuery);
        examQuestion.setQuestionItemList(questionItemList);
        return getSuccessResponseVO(examQuestion);
    }

    @RequestMapping("/report")
    @GlobalInterceptor(frequencyType = RequestFrequencyTypeEnum.DAY,requestFrequencyThreshold = 10)//10次/天
    public ResponseVo report(HttpServletRequest request,
                             @VerifyParam(required = true, max = 32) String deviceId,
                             @VerifyParam(max = 30) String deviceBrand) {
        AppDevice appDevice = new AppDevice();
        appDevice.setDeviceId(deviceId);
        appDevice.setDeviceBrand(deviceBrand);
        appDevice.setIp(getIpAddr(request));
        appDeviceService.reportData(appDevice);
        return getSuccessResponseVO(null);
    }
}
