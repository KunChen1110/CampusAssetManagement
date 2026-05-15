package com.campus.asset.controller.admin;

import com.campus.asset.result.Result;
import com.campus.asset.service.AssetStatisticsService;
import com.campus.asset.vo.AssetCategoryDistributionVO;
import com.campus.asset.vo.AssetStatisticsSummaryVO;
import com.campus.asset.vo.AssetUsageRankingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/asset-statistics")
@Api(tags = "Asset statistics APIs")
public class AssetStatisticsController {

    @Autowired
    private AssetStatisticsService assetStatisticsService;

    @GetMapping("/summary")
    @ApiOperation("Asset statistics summary")
    public Result<AssetStatisticsSummaryVO> summary() {
        return Result.success(assetStatisticsService.summary());
    }

    @GetMapping("/top-used")
    @ApiOperation("Top used assets")
    public Result<List<AssetUsageRankingVO>> topUsed(@RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(assetStatisticsService.topUsed(limit));
    }

    @GetMapping("/category-distribution")
    @ApiOperation("Asset category distribution")
    public Result<List<AssetCategoryDistributionVO>> categoryDistribution() {
        return Result.success(assetStatisticsService.categoryDistribution());
    }
}
