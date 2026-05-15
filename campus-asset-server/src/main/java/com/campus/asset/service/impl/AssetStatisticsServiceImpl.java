package com.campus.asset.service.impl;

import com.campus.asset.mapper.AssetStatisticsMapper;
import com.campus.asset.service.AssetStatisticsService;
import com.campus.asset.vo.AssetCategoryDistributionVO;
import com.campus.asset.vo.AssetStatisticsSummaryVO;
import com.campus.asset.vo.AssetUsageRankingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AssetStatisticsServiceImpl implements AssetStatisticsService {

    private static final String SUMMARY_KEY = "asset:statistics:summary";
    private static final String CATEGORY_DISTRIBUTION_KEY = "asset:statistics:category-distribution";
    private static final String TOP_USED_KEY_PREFIX = "asset:statistics:top-used:";
    private static final long CACHE_TTL_MINUTES = 10L;

    @Autowired
    private AssetStatisticsMapper assetStatisticsMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public AssetStatisticsSummaryVO summary() {
        Object cached = redisTemplate.opsForValue().get(SUMMARY_KEY);
        if (cached instanceof AssetStatisticsSummaryVO) {
            return (AssetStatisticsSummaryVO) cached;
        }
        AssetStatisticsSummaryVO summary = assetStatisticsMapper.summary();
        redisTemplate.opsForValue().set(SUMMARY_KEY, summary, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        return summary;
    }

    @Override
    public List<AssetUsageRankingVO> topUsed(Integer limit) {
        int safeLimit = limit == null || limit <= 0 ? 10 : Math.min(limit, 100);
        String key = TOP_USED_KEY_PREFIX + safeLimit;
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached instanceof List) {
            return (List<AssetUsageRankingVO>) cached;
        }
        List<AssetUsageRankingVO> result = assetStatisticsMapper.topUsed(safeLimit);
        redisTemplate.opsForValue().set(key, result, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public List<AssetCategoryDistributionVO> categoryDistribution() {
        Object cached = redisTemplate.opsForValue().get(CATEGORY_DISTRIBUTION_KEY);
        if (cached instanceof List) {
            return (List<AssetCategoryDistributionVO>) cached;
        }
        List<AssetCategoryDistributionVO> result = assetStatisticsMapper.categoryDistribution();
        redisTemplate.opsForValue().set(CATEGORY_DISTRIBUTION_KEY, result, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        return result;
    }
}
