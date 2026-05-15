package com.campus.asset.service;

import com.campus.asset.dto.AssetOperationLogPageQueryDTO;
import com.campus.asset.result.PageResult;

public interface AssetOperationLogService {

    PageResult pageQuery(AssetOperationLogPageQueryDTO queryDTO);
}
