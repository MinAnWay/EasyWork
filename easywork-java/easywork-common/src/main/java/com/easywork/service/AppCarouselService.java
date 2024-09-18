package com.easywork.service;

import com.easywork.entity.vo.PaginationResultVo;
import com.easywork.entity.po.AppCarousel;
import com.easywork.entity.query.AppCarouselQuery;

import java.util.List;
	/**
	 * app轮播图Service
	 */
public interface AppCarouselService {

	/**
	 * 根据条件查询列表
	 */
	List<AppCarousel> findListByParam(AppCarouselQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(AppCarouselQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<AppCarousel> findListByPage(AppCarouselQuery query);

	/**
	 * 新增
	 */
	Integer add(AppCarousel bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<AppCarousel> ListBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<AppCarousel> ListBean);

	/**
	 * 根据CarouselId查询
	 */
	AppCarousel getAppCarouselByCarouselId(Integer carouselId);

	/**
	 * 根据CarouselId更新
	 */
	Integer updateAppCarouselByCarouselId(AppCarousel bean, Integer carouselId);

	/**
	 * 根据CarouselId删除
	 */
	Integer deleteAppCarouselByCarouselId(Integer carouselId);

	void saveCarousel(AppCarousel bean);

	void changeSort(String carouselIds);

}