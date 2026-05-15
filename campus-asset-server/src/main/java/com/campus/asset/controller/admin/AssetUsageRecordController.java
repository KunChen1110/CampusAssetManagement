package com.campus.asset.controller.admin;

import com.campus.asset.dto.AssetUsageRecordDTO;
import com.campus.asset.dto.AssetUsageRecordPageQueryDTO;
import com.campus.asset.result.PageResult;
import com.campus.asset.result.Result;
import com.campus.asset.service.AssetUsageRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/asset-usage")
@Api(tags = "Asset usage APIs")
public class AssetUsageRecordController {

    @Autowired
    private AssetUsageRecordService assetUsageRecordService;

    @PostMapping
    @ApiOperation("Create asset usage record")
    public Result save(@RequestBody AssetUsageRecordDTO usageRecordDTO) {
        assetUsageRecordService.save(usageRecordDTO);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("Update asset usage record")
    public Result update(@RequestBody AssetUsageRecordDTO usageRecordDTO) {
        assetUsageRecordService.update(usageRecordDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete asset usage record")
    public Result delete(@PathVariable Long id) {
        assetUsageRecordService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("Page asset usage records")
    public Result<PageResult> page(AssetUsageRecordPageQueryDTO queryDTO) {
        return Result.success(assetUsageRecordService.pageQuery(queryDTO));
    }
}
