package com.campus.asset.service.impl;

import com.campus.asset.context.BaseContext;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.campus.asset.dto.AssetDTO;
import com.campus.asset.dto.AssetPageQueryDTO;
import com.campus.asset.dto.AssetStatusUpdateDTO;
import com.campus.asset.entity.Asset;
import com.campus.asset.entity.AssetOperationLog;
import com.campus.asset.exception.BaseException;
import com.campus.asset.mapper.AssetMapper;
import com.campus.asset.mapper.AssetOperationLogMapper;
import com.campus.asset.result.PageResult;
import com.campus.asset.service.AssetCacheService;
import com.campus.asset.service.AssetService;
import com.campus.asset.service.AssetStatusTransitionPolicy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private AssetCacheService assetCacheService;

    @Autowired
    private AssetOperationLogMapper assetOperationLogMapper;

    @Autowired
    private AssetStatusTransitionPolicy statusTransitionPolicy;

    @Override
    public void save(AssetDTO assetDTO) {
        Asset asset = new Asset();
        BeanUtils.copyProperties(assetDTO, asset);
        LocalDateTime now = LocalDateTime.now();
        asset.setCreateTime(now);
        asset.setUpdateTime(now);
        if (asset.getStatus() == null) {
            asset.setStatus("IDLE");
        }
        assetMapper.insert(asset);
        writeLog(asset.getId(), "CREATE", null, asset.getStatus(), "Asset created");
        assetCacheService.evictStatistics();
    }

    @Override
    public void update(AssetDTO assetDTO) {
        Asset asset = new Asset();
        BeanUtils.copyProperties(assetDTO, asset);
        asset.setUpdateTime(LocalDateTime.now());
        assetMapper.update(asset);
        writeLog(asset.getId(), "UPDATE", null, asset.getStatus(), "Asset basic information updated");
        assetCacheService.evictStatistics();
    }

    @Override
    public void deleteById(Long id) {
        Asset existing = assetMapper.getById(id);
        assetMapper.deleteById(id);
        writeLog(id, "DELETE", existing == null ? null : existing.getStatus(), null, "Asset deleted");
        assetCacheService.evictStatistics();
    }

    @Override
    public PageResult pageQuery(AssetPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<Asset> page = assetMapper.pageQuery(queryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Asset getById(Long id) {
        return assetMapper.getById(id);
    }

    @Override
    public void updateStatus(Long id, AssetStatusUpdateDTO statusUpdateDTO) {
        Asset existing = assetMapper.getById(id);
        if (existing == null) {
            throw new BaseException("Asset does not exist");
        }
        String targetStatus = statusUpdateDTO.getStatus();
        if (!statusTransitionPolicy.canTransit(existing.getStatus(), targetStatus)) {
            throw new BaseException("Illegal asset status transition: " + existing.getStatus() + " -> " + targetStatus);
        }
        Asset asset = Asset.builder()
                .id(id)
                .status(targetStatus)
                .updateTime(LocalDateTime.now())
                .build();
        assetMapper.update(asset);
        writeLog(id, "STATUS_CHANGE", existing.getStatus(), targetStatus, statusUpdateDTO.getRemark());
        assetCacheService.evictStatistics();
    }

    private void writeLog(Long assetId, String operationType, String oldStatus, String newStatus, String remark) {
        AssetOperationLog operationLog = AssetOperationLog.builder()
                .assetId(assetId)
                .operationType(operationType)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .remark(remark)
                .operatorId(BaseContext.getCurrentId())
                .createTime(LocalDateTime.now())
                .build();
        assetOperationLogMapper.insert(operationLog);
    }
}
