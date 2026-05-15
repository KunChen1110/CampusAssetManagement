package com.campus.asset.query;

import com.campus.asset.mapper.AssetQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryExecutionService {

    @Autowired
    private AssetQueryMapper assetQueryMapper;

    public List<Map<String, Object>> execute(QuerySqlBuilder.BuiltQuery builtQuery) {
        return assetQueryMapper.execute(builtQuery);
    }
}
