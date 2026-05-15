package com.campus.asset.service;

import com.campus.asset.dto.AssetDTO;
import com.campus.asset.dto.AssetPageQueryDTO;
import com.campus.asset.dto.AssetStatusUpdateDTO;
import com.campus.asset.entity.Asset;
import com.campus.asset.result.PageResult;

public interface AssetService {

    void save(AssetDTO assetDTO);

    void update(AssetDTO assetDTO);

    void deleteById(Long id);

    PageResult pageQuery(AssetPageQueryDTO queryDTO);

    Asset getById(Long id);

    void updateStatus(Long id, AssetStatusUpdateDTO statusUpdateDTO);
}
