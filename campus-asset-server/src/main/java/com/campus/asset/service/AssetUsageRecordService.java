package com.campus.asset.service;

import com.campus.asset.dto.AssetUsageRecordDTO;
import com.campus.asset.dto.AssetUsageRecordPageQueryDTO;
import com.campus.asset.result.PageResult;

public interface AssetUsageRecordService {

    void save(AssetUsageRecordDTO usageRecordDTO);

    void update(AssetUsageRecordDTO usageRecordDTO);

    void deleteById(Long id);

    PageResult pageQuery(AssetUsageRecordPageQueryDTO queryDTO);
}
