package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.service.StatisticsDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description 首页数据统计
 *
 * @author Andrew
 * @date 2024/8/7 16:58:13
 **/
@RestController()
@RequestMapping("/index")
public class IndexController extends ABaseController {
    @Resource
    private StatisticsDataService statisticsDataService;

    @RequestMapping("/getAllData")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.HOME)
    public ResponseVo getAllDetail() {
        return getSuccessResponseVO(statisticsDataService.getAllData());
    }

    @RequestMapping("/getAppWeekData")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.HOME)
    public ResponseVo getAppWeekData() {
        return getSuccessResponseVO(statisticsDataService.getAppWeekData());
    }

    @RequestMapping("/getContentWeekData")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.HOME)
    public ResponseVo getContentWeekData() {
        return getSuccessResponseVO(statisticsDataService.getContentWeekData());
    }
}
