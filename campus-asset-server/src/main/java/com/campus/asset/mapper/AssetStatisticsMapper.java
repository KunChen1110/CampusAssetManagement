package com.campus.asset.mapper;

import com.campus.asset.vo.AssetCategoryDistributionVO;
import com.campus.asset.vo.AssetStatisticsSummaryVO;
import com.campus.asset.vo.AssetUsageRankingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssetStatisticsMapper {

    AssetStatisticsSummaryVO summary();

    List<AssetUsageRankingVO> topUsed(@Param("limit") Integer limit);

    List<AssetCategoryDistributionVO> categoryDistribution();
}
