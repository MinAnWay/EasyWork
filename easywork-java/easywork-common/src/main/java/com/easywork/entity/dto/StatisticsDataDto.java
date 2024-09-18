package com.easywork.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/7 17:04:47
 **/
@Data
public class StatisticsDataDto {
    private String statisticsName;
    private Integer count;
    private Integer preCount;
    private List<Integer> listData;
}
