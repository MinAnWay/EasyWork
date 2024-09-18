package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.dto.CreateImageCode;
import com.easywork.entity.dto.SessionUserAdminDto;
import com.easywork.entity.po.SysAccount;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.enums.VerifyRegexEnum;
import com.easywork.exception.BusinessException;
import com.easywork.service.SysAccountService;
import com.easywork.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController("loginController")
public class LoginController extends ABaseController {
    @Resource
    private SysAccountService sysAccountService;

    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();
        session.setAttribute(Constants.CHECK_CODE_KEY, code);
        vCode.write(response.getOutputStream());
    }

    @RequestMapping("/login")
    @GlobalInterceptor(checkLogin = false)
    public ResponseVo login(HttpSession session,
                            @VerifyParam(regex = VerifyRegexEnum.PHONE) String phone,
                            @VerifyParam(required = true) String password,
                            @VerifyParam(required = true) String checkCode) throws BusinessException {
        if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
            throw new BusinessException("图片验证码错误");
        }
        SessionUserAdminDto userAdminDto = sysAccountService.login(phone, password);
        session.setAttribute(Constants.SESSION_KEY, userAdminDto);
        return getSuccessResponseVO(userAdminDto);
    }

    @RequestMapping("/logout")
    public ResponseVo logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updateMyPwd")
    public ResponseVo updateMyPwd(HttpSession session, @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD) String password) {
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(session);
        SysAccount sysAccount = new SysAccount();
        sysAccount.setPassword(StringTools.encodeMd5(password));
        sysAccountService.updateAccountByUserId(sysAccount, userAdminDto.getUserId());
        return getSuccessResponseVO(null);
    }
}
