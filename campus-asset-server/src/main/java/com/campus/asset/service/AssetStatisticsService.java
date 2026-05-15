package com.campus.asset.service;

import com.campus.asset.vo.AssetCategoryDistributionVO;
import com.campus.asset.vo.AssetStatisticsSummaryVO;
import com.campus.asset.vo.AssetUsageRankingVO;

import java.util.List;

public interface AssetStatisticsService {

    AssetStatisticsSummaryVO summary();

    List<AssetUsageRankingVO> topUsed(Integer limit);

    List<AssetCategoryDistributionVO> categoryDistribution();
}
