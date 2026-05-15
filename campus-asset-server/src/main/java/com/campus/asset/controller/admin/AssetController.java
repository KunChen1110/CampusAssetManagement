package com.campus.asset.controller.admin;

import com.campus.asset.dto.AssetDTO;
import com.campus.asset.dto.AssetPageQueryDTO;
import com.campus.asset.dto.AssetStatusUpdateDTO;
import com.campus.asset.entity.Asset;
import com.campus.asset.result.PageResult;
import com.campus.asset.result.Result;
import com.campus.asset.service.AssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/asset")
@Api(tags = "Asset APIs")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping
    @ApiOperation("Create asset")
    public Result save(@RequestBody AssetDTO assetDTO) {
        assetService.save(assetDTO);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("Update asset")
    public Result update(@RequestBody AssetDTO assetDTO) {
        assetService.update(assetDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete asset")
    public Result delete(@PathVariable Long id) {
        assetService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("Page assets")
    public Result<PageResult> page(AssetPageQueryDTO queryDTO) {
        return Result.success(assetService.pageQuery(queryDTO));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get asset by id")
    public Result<Asset> getById(@PathVariable Long id) {
        return Result.success(assetService.getById(id));
    }

    @PutMapping("/{id}/status")
    @ApiOperation("Update asset status with lifecycle validation")
    public Result updateStatus(@PathVariable Long id, @RequestBody AssetStatusUpdateDTO statusUpdateDTO) {
        assetService.updateStatus(id, statusUpdateDTO);
        return Result.success();
    }
}
