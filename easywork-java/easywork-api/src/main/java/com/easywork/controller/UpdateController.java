package com.easywork.controller;

import com.easywork.annotation.GlobalInterceptor;
import com.easywork.annotation.VerifyParam;
import com.easywork.entity.config.AppConfig;
import com.easywork.entity.constants.Constants;
import com.easywork.entity.po.AppUpdate;
import com.easywork.entity.vo.ResponseVo;
import com.easywork.entity.vo.app.AppUpdateVo;
import com.easywork.enums.AppUpdateTypeEnum;
import com.easywork.enums.RequestFrequencyTypeEnum;
import com.easywork.service.AppUpdateService;
import com.easywork.utils.CopyTools;
import com.easywork.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Description 应用更新
 *
 * @author Andrew
 * @date 2024/8/10 16:33:18
 **/
@RestController
@RequestMapping("/update")
public class UpdateController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Resource
    private AppConfig appConfig;

    @Resource
    private AppUpdateService appUpdateService;

    @RequestMapping("/checkVersion")
    @GlobalInterceptor
    public ResponseVo checkVersion(String appVersion,
                                   @VerifyParam(required = true) String deviceId) {
        if (StringTools.isEmpty(appVersion)) {
            return getSuccessResponseVO(null);
        }
        AppUpdate appUpdate = appUpdateService.getLatestUpdate(appVersion, deviceId);
        if (appUpdate == null) {
            return getSuccessResponseVO(null);
        }
        AppUpdateVo updateVo = CopyTools.copy(appUpdate, AppUpdateVo.class);
        AppUpdateTypeEnum typeEnum = AppUpdateTypeEnum.getByType(appUpdate.getUpdateType());
        File file = new File(appConfig.getProjectFolder() + Constants.APP_UPDATE_FOLDER + appUpdate.getId() + typeEnum.getSuffix());
        updateVo.setSize(file.length());
        updateVo.setUpdateList(Arrays.asList(appUpdate.getUpdateDescArray()));
        return getSuccessResponseVO(updateVo);
    }

    @RequestMapping("/download")
    @GlobalInterceptor(frequencyType = RequestFrequencyTypeEnum.DAY, requestFrequencyThreshold = 10)
    public void download(HttpServletResponse response, @VerifyParam(required = true) Integer id) throws IOException {
        OutputStream out = null;
        FileInputStream in = null;
        try {
            AppUpdate appUpdate = appUpdateService.getAppUpdateById(id);

            AppUpdateTypeEnum typeEnum = AppUpdateTypeEnum.getByType(appUpdate.getUpdateType());
            String fileName = appConfig.getAppName() + "." + appUpdate.getVersion() + typeEnum.getSuffix();
            File file = new File(appConfig.getProjectFolder() + Constants.APP_UPDATE_FOLDER + appUpdate.getId() + typeEnum.getSuffix());
            if (!file.exists()) {
                return;
            }
            response.setContentType("application/x-msdownload; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            response.setContentLengthLong(file.length());

            in = new FileInputStream(file);
            byte[] byteData = new byte[1024];
            out = response.getOutputStream();
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("下载失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("IO异常", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("IO异常", e);
                }
            }
        }
    }
}
