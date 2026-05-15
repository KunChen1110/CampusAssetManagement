package com.campus.asset.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.campus.asset.constant.MessageConstant;
import com.campus.asset.dto.AssetCategoryDTO;
import com.campus.asset.dto.AssetCategoryPageQueryDTO;
import com.campus.asset.entity.AssetCategory;
import com.campus.asset.exception.DeletionNotAllowedException;
import com.campus.asset.mapper.AssetCategoryMapper;
import com.campus.asset.result.PageResult;
import com.campus.asset.service.AssetCacheService;
import com.campus.asset.service.AssetCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService {

    @Autowired
    private AssetCategoryMapper assetCategoryMapper;

    @Autowired
    private AssetCacheService assetCacheService;

    @Override
    public void save(AssetCategoryDTO assetCategoryDTO) {
        AssetCategory assetCategory = new AssetCategory();
        BeanUtils.copyProperties(assetCategoryDTO, assetCategory);
        LocalDateTime now = LocalDateTime.now();
        assetCategory.setCreateTime(now);
        assetCategory.setUpdateTime(now);
        if (assetCategory.getStatus() == null) {
            assetCategory.setStatus(1);
        }
        assetCategoryMapper.insert(assetCategory);
        assetCacheService.evictStatistics();
    }

    @Override
    public void update(AssetCategoryDTO assetCategoryDTO) {
        AssetCategory assetCategory = new AssetCategory();
        BeanUtils.copyProperties(assetCategoryDTO, assetCategory);
        assetCategory.setUpdateTime(LocalDateTime.now());
        assetCategoryMapper.update(assetCategory);
        assetCacheService.evictStatistics();
    }

    @Override
    public void deleteById(Long id) {
        Integer count = assetCategoryMapper.countAssetsByCategoryId(id);
        if (count != null && count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.ASSET_CATEGORY_IN_USE);
        }
        assetCategoryMapper.deleteById(id);
        assetCacheService.evictStatistics();
    }

    @Override
    public PageResult pageQuery(AssetCategoryPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<AssetCategory> page = assetCategoryMapper.pageQuery(queryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<AssetCategory> list(AssetCategoryPageQueryDTO queryDTO) {
        return assetCategoryMapper.list(queryDTO);
    }
}
