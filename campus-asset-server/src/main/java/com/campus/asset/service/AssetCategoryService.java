package com.campus.asset.service;

import com.campus.asset.dto.AssetCategoryDTO;
import com.campus.asset.dto.AssetCategoryPageQueryDTO;
import com.campus.asset.entity.AssetCategory;
import com.campus.asset.result.PageResult;

import java.util.List;

public interface AssetCategoryService {

    void save(AssetCategoryDTO assetCategoryDTO);

    void update(AssetCategoryDTO assetCategoryDTO);

    void deleteById(Long id);

    PageResult pageQuery(AssetCategoryPageQueryDTO queryDTO);

    List<AssetCategory> list(AssetCategoryPageQueryDTO queryDTO);
}
