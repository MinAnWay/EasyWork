package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.po.SysRole;
import com.easywork.entity.query.RoleQuery;
import com.easywork.enums.PermissionCodeEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.SysRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:系统角色表Controller
 * @author:AndrewWay
 * @date:2024/08/01
 */
@RestController("sysRoleController")
@RequestMapping("/settings")
public class SysRoleController extends ABaseController {

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/loadRole")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public ResponseVo loadRole(RoleQuery query) {
        query.setOrderBy("create_time desc");
        return getSuccessResponseVO(sysRoleService.findListByPage(query));
    }

    @RequestMapping("/loadAllRoles")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public ResponseVo loadAllRoles() {
        RoleQuery query = new RoleQuery();
        query.setOrderBy("create_time desc");
        return getSuccessResponseVO(sysRoleService.findListByParam(query));
    }

    /**
     * 新增
     */

    @RequestMapping("/saveRole")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public ResponseVo saveRole(@VerifyParam SysRole bean,
                               String menuIds,
                               String halfMenuIds) throws BusinessException {//halfMenuIds 半选菜单id
        sysRoleService.saveRole(bean, menuIds, halfMenuIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/saveRoleMenu")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public ResponseVo saveRoleMenu(@VerifyParam(required = true) Integer roleId,
                                   @VerifyParam(required = true) String menuIds,
                                   String halfMenuIds) throws BusinessException {//halfMenuIds 半选菜单id
        sysRoleService.saveRoleMenu(roleId, menuIds, halfMenuIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/getRoleByRoleId")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public ResponseVo getRoleByRoleId(@VerifyParam(required = true) Integer roleId) {
        SysRole sysRole = sysRoleService.getRoleByRoleId(roleId);
        return getSuccessResponseVO(sysRole);
    }

    @RequestMapping("/delRole")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public ResponseVo delRole(@VerifyParam(required = true) Integer roleId) throws BusinessException {
        sysRoleService.deleteRoleByRoleId(roleId);
        return getSuccessResponseVO(null);
    }
}