package com.campus.asset.service.impl;

import com.campus.asset.service.AssetCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AssetCacheServiceImpl implements AssetCacheService {

    private static final String STATISTICS_KEY_PATTERN = "asset:statistics:*";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void evictStatistics() {
        Set<Object> keys = redisTemplate.keys(STATISTICS_KEY_PATTERN);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
