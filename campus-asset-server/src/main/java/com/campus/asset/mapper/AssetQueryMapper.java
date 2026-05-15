package com.campus.asset.mapper;

import com.campus.asset.query.QuerySqlBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface AssetQueryMapper {

    @SelectProvider(type = AssetQuerySqlProvider.class, method = "provide")
    List<Map<String, Object>> execute(@Param("query") QuerySqlBuilder.BuiltQuery query);
}
