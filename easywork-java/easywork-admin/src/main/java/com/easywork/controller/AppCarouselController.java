package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.AppCarousel;
import com.easywork.entity.query.AppCarouselQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.service.AppCarouselService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:app轮播图Controller
 * @author:AndrewWay
 * @date:2024/08/07
 */
@RestController("appCarouselController")
@RequestMapping("/appCarousel")
public class AppCarouselController extends ABaseController {

	@Resource
	private AppCarouselService appCarouselService;
	@RequestMapping("/loadDataList")
	@GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_LIST)
	public ResponseVo loadDataList(AppCarouselQuery query) {
		query.setOrderBy("sort asc");
		return getSuccessResponseVO(appCarouselService.findCountByParam(query));
	}

	@RequestMapping("/saveCarousel")
	@GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
	public ResponseVo saveCarousel(AppCarousel appCarousel){
		appCarouselService.saveCarousel(appCarousel);
		return getSuccessResponseVO(null);
	}

	@RequestMapping("/delCarousel")
	@GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
	public ResponseVo delCarousel(@VerifyParam(required = true) Integer carouselId){
		appCarouselService.deleteAppCarouselByCarouselId(carouselId);
		return getSuccessResponseVO(null);
	}

	@RequestMapping("/changeSort")
	@GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
	public ResponseVo changeSort(@VerifyParam(required = true) String carouselIds){
		appCarouselService.changeSort(carouselIds);
		return getSuccessResponseVO(null);
	}
}