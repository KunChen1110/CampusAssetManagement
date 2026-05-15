package com.campus.asset.controller.admin;

import com.campus.asset.dto.AssetCategoryDTO;
import com.campus.asset.dto.AssetCategoryPageQueryDTO;
import com.campus.asset.entity.AssetCategory;
import com.campus.asset.result.PageResult;
import com.campus.asset.result.Result;
import com.campus.asset.service.AssetCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/asset-category")
@Api(tags = "Asset category APIs")
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;

    @PostMapping
    @ApiOperation("Create asset category")
    public Result save(@RequestBody AssetCategoryDTO assetCategoryDTO) {
        assetCategoryService.save(assetCategoryDTO);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("Update asset category")
    public Result update(@RequestBody AssetCategoryDTO assetCategoryDTO) {
        assetCategoryService.update(assetCategoryDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete asset category")
    public Result delete(@PathVariable Long id) {
        assetCategoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("Page asset categories")
    public Result<PageResult> page(AssetCategoryPageQueryDTO queryDTO) {
        return Result.success(assetCategoryService.pageQuery(queryDTO));
    }

    @GetMapping("/list")
    @ApiOperation("List asset categories")
    public Result<List<AssetCategory>> list(AssetCategoryPageQueryDTO queryDTO) {
        return Result.success(assetCategoryService.list(queryDTO));
    }
}
