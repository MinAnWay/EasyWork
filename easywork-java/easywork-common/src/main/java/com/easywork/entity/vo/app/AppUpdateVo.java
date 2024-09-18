package com.easywork.entity.vo.app;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/10 16:39:45
 **/
@Data
public class AppUpdateVo implements Serializable {
    private static final long serialVersionUID = 5424759340528671116L;

    private Integer id;

    /**
     * 版本号
     * */
    private String version;

    /**
     * 更新描述
     * */
    private List<String> updateList;

    private Long size;
}
