package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.po.SysMenu;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.query.MenuQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.service.SysMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description:菜单表Controller
 * @author:AndrewWay
 * @date:2024/08/01
 */
@RestController()
@RequestMapping("/settings")
public class SysMenuController extends ABaseController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 根据条件查询
     */
    @RequestMapping("/menuList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_MENU)
    public ResponseVo menuList() {
        MenuQuery query = new MenuQuery();
        query.setFormate2Tree(true);
        query.setOrderBy("sort asc");
        List<SysMenu> sysSysMenuList = sysMenuService.findListByParam(query);
        return getSuccessResponseVO(sysSysMenuList);
    }

    @RequestMapping("/saveMenu")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_MENU_EDIT)
    public ResponseVo saveMenu(@VerifyParam SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delMenu")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_MENU_EDIT)
    public ResponseVo delMenu(@VerifyParam(required = true) Integer menuId) {
        sysMenuService.deleteMenuByMenuId(menuId);
        return getSuccessResponseVO(null);
    }
}