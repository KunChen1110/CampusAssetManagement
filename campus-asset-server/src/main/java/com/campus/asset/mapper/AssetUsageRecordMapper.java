package com.campus.asset.mapper;

import com.github.pagehelper.Page;
import com.campus.asset.dto.AssetUsageRecordPageQueryDTO;
import com.campus.asset.entity.AssetUsageRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetUsageRecordMapper {

    @Insert("insert into asset_usage_record(asset_id, user_name, department, usage_start_time, usage_end_time, purpose, status, create_time, update_time) " +
            "values(#{assetId}, #{userName}, #{department}, #{usageStartTime}, #{usageEndTime}, #{purpose}, #{status}, #{createTime}, #{updateTime})")
    void insert(AssetUsageRecord usageRecord);

    void update(AssetUsageRecord usageRecord);

    @Delete("delete from asset_usage_record where id = #{id}")
    void deleteById(Long id);

    Page<AssetUsageRecord> pageQuery(AssetUsageRecordPageQueryDTO queryDTO);
}
