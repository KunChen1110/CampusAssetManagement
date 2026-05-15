package com.campus.asset.query;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueryPlanParserTest {

    private final QueryPlanParser parser = new QueryPlanParser();
    private final QuerySqlBuilder sqlBuilder = new QuerySqlBuilder();

    @Test
    void parsesIdleAssetsInLocation() {
        QueryPlan plan = parser.parse("查询教学楼A的闲置设备");

        assertEquals(QueryMetric.COUNT_ASSETS, plan.getMetric());
        assertTrue(plan.getDimensions().contains(QueryDimension.ASSET));
        assertEquals("IDLE", plan.getFilters().getStatus());
        assertEquals("教学楼A", plan.getFilters().getLocation());
    }

    @Test
    void parsesRecentCategoryUsageRanking() {
        QueryPlan plan = parser.parse("最近三个月使用次数最多的资产类别是什么？");

        assertEquals(QueryMetric.CATEGORY_USAGE_RANKING, plan.getMetric());
        assertTrue(plan.getDimensions().contains(QueryDimension.CATEGORY));
        assertEquals(QueryTimeRange.Type.LAST_MONTHS, plan.getTimeRange().getType());
        assertEquals(3, plan.getTimeRange().getAmount());
        assertEquals("usageCount", plan.getSort().getField());
        assertEquals("desc", plan.getSort().getDirection());
    }

    @Test
    void sqlBuilderDoesNotInlineFilterValues() {
        QueryPlan plan = parser.parse("查询教学楼A的闲置设备");
        QuerySqlBuilder.BuiltQuery builtQuery = sqlBuilder.build(plan);

        assertTrue(builtQuery.getSql().contains("#{status}"));
        assertTrue(builtQuery.getSql().contains("#{location}"));
        assertFalse(builtQuery.getSql().contains("教学楼A"));
        assertEquals("IDLE", builtQuery.getParameters().get("status"));
        assertEquals("教学楼A", builtQuery.getParameters().get("location"));
    }

    @Test
    void parsesMaintenanceAssetCount() {
        QueryPlan plan = parser.parse("维修中的资产有多少？");
        QuerySqlBuilder.BuiltQuery builtQuery = sqlBuilder.build(plan);

        assertEquals(QueryMetric.COUNT_ASSETS, plan.getMetric());
        assertEquals("MAINTENANCE", plan.getFilters().getStatus());
        assertTrue(builtQuery.getSql().contains("count(*) as totalAssets"));
        assertTrue(builtQuery.getSql().contains("#{status}"));
    }

    @Test
    void parsesDepartmentUsageRanking() {
        QueryPlan plan = parser.parse("哪个部门使用资产最多？");

        assertEquals(QueryMetric.DEPARTMENT_USAGE_RANKING, plan.getMetric());
        assertTrue(plan.getDimensions().contains(QueryDimension.DEPARTMENT));
        assertEquals("usageCount", plan.getSort().getField());
    }
}
