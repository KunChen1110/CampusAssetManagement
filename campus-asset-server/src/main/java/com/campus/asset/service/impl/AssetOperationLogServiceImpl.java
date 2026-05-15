package com.campus.asset.service.impl;

import com.campus.asset.dto.AssetOperationLogPageQueryDTO;
import com.campus.asset.entity.AssetOperationLog;
import com.campus.asset.mapper.AssetOperationLogMapper;
import com.campus.asset.result.PageResult;
import com.campus.asset.service.AssetOperationLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetOperationLogServiceImpl implements AssetOperationLogService {

    @Autowired
    private AssetOperationLogMapper assetOperationLogMapper;

    @Override
    public PageResult pageQuery(AssetOperationLogPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<AssetOperationLog> page = assetOperationLogMapper.pageQuery(queryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
