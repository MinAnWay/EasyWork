package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.po.Category;
import com.easywork.entity.query.CategoryQuery;
import com.easywork.entity.vo.ResponseVo;

import com.easywork.enums.PermissionCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:分类表Controller
 * @author:AndrewWay
 * @date:2024/08/03
 */
@RestController()
@RequestMapping("/category")
public class CategoryController extends ABaseController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping("/loadAllCategory")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEGORY_LIST)
    public ResponseVo loadAllCategory(CategoryQuery query) {
        query.setOrderBy("sort asc");
        return getSuccessResponseVO(categoryService.findListByParam(query));
    }

    @RequestMapping("/saveCategory")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEGORY_EDIT)
    public ResponseVo saveCategory(Category category) throws BusinessException {
        categoryService.saveCategory(category);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delCategory")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEGORY_EDIT)
    public ResponseVo delCategory(@VerifyParam(required = true) Integer categoryId) throws BusinessException {
        categoryService.deleteCategoryByCategoryId(categoryId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/changeSort")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEGORY_EDIT)
    public ResponseVo changeSort(@VerifyParam(required = true) String categoryIds) {
        categoryService.changeSort(categoryIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadAllCategory4Select")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEGORY_LIST)
    public ResponseVo loadAllCategory4Select(@VerifyParam(required = true) Integer type) throws BusinessException {
        List<Category> list = categoryService.loadAllCategoryByType(type);
        return getSuccessResponseVO(list);
    }
}