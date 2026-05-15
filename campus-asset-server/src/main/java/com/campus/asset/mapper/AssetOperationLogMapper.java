package com.campus.asset.mapper;

import com.campus.asset.dto.AssetOperationLogPageQueryDTO;
import com.campus.asset.entity.AssetOperationLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetOperationLogMapper {

    @Insert("insert into asset_operation_log(asset_id, operation_type, old_status, new_status, remark, operator_id, create_time) " +
            "values(#{assetId}, #{operationType}, #{oldStatus}, #{newStatus}, #{remark}, #{operatorId}, #{createTime})")
    void insert(AssetOperationLog operationLog);

    Page<AssetOperationLog> pageQuery(AssetOperationLogPageQueryDTO queryDTO);
}
