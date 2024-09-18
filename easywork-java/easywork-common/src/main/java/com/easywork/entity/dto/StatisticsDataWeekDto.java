package com.easywork.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/7 17:07:48
 **/
@Data
public class StatisticsDataWeekDto {
    private List<String> dataList;
    private List<StatisticsDataDto> itemDataList;
}
