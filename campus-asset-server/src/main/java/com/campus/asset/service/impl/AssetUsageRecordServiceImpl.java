package com.campus.asset.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.campus.asset.dto.AssetUsageRecordDTO;
import com.campus.asset.dto.AssetUsageRecordPageQueryDTO;
import com.campus.asset.entity.AssetUsageRecord;
import com.campus.asset.mapper.AssetUsageRecordMapper;
import com.campus.asset.result.PageResult;
import com.campus.asset.service.AssetCacheService;
import com.campus.asset.service.AssetUsageRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssetUsageRecordServiceImpl implements AssetUsageRecordService {

    @Autowired
    private AssetUsageRecordMapper usageRecordMapper;

    @Autowired
    private AssetCacheService assetCacheService;

    @Override
    public void save(AssetUsageRecordDTO usageRecordDTO) {
        AssetUsageRecord usageRecord = new AssetUsageRecord();
        BeanUtils.copyProperties(usageRecordDTO, usageRecord);
        LocalDateTime now = LocalDateTime.now();
        usageRecord.setCreateTime(now);
        usageRecord.setUpdateTime(now);
        if (usageRecord.getStatus() == null) {
            usageRecord.setStatus("IN_USE");
        }
        usageRecordMapper.insert(usageRecord);
        assetCacheService.evictStatistics();
    }

    @Override
    public void update(AssetUsageRecordDTO usageRecordDTO) {
        AssetUsageRecord usageRecord = new AssetUsageRecord();
        BeanUtils.copyProperties(usageRecordDTO, usageRecord);
        usageRecord.setUpdateTime(LocalDateTime.now());
        usageRecordMapper.update(usageRecord);
        assetCacheService.evictStatistics();
    }

    @Override
    public void deleteById(Long id) {
        usageRecordMapper.deleteById(id);
        assetCacheService.evictStatistics();
    }

    @Override
    public PageResult pageQuery(AssetUsageRecordPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<AssetUsageRecord> page = usageRecordMapper.pageQuery(queryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
