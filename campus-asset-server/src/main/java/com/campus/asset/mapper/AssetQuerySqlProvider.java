package com.campus.asset.mapper;

import com.campus.asset.query.QuerySqlBuilder;

import java.util.Map;

public class AssetQuerySqlProvider {

    public String provide(Map<String, Object> params) {
        QuerySqlBuilder.BuiltQuery query = (QuerySqlBuilder.BuiltQuery) params.get("query");
        return query.getMyBatisSql();
    }
}
