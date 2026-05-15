package com.campus.asset.controller.admin;

import com.campus.asset.dto.AssetOperationLogPageQueryDTO;
import com.campus.asset.result.PageResult;
import com.campus.asset.result.Result;
import com.campus.asset.service.AssetOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/asset-log")
@Api(tags = "Asset operation log APIs")
public class AssetOperationLogController {

    @Autowired
    private AssetOperationLogService assetOperationLogService;

    @GetMapping("/page")
    @ApiOperation("Page asset operation logs")
    public Result<PageResult> page(AssetOperationLogPageQueryDTO queryDTO) {
        return Result.success(assetOperationLogService.pageQuery(queryDTO));
    }
}
