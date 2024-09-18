package com.easywork.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * Description
 *
 * @author Andrew
 * @date 2024/8/5 00:50:00
 **/
public interface ACommonMapper {
    Integer updateCount(
            @Param("tableName") String tableName,
            @Param("readCount") Integer readCount,
            @Param("collectionCount") Integer collectionCount,
            @Param("keyId") Integer keyId
    );
}
