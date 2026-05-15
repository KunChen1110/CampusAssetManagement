package com.campus.asset.query;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class QueryPlanParser {

    public QueryPlan parse(String question) {
        String text = question == null ? "" : question.trim();
        String normalized = text.toLowerCase(Locale.ROOT);

        QueryPlan plan = new QueryPlan();
        plan.setMetric(QueryMetric.COUNT_ASSETS);
        plan.setTimeRange(QueryTimeRange.allTime());

        if (containsAny(normalized, "最近三个月", "last 3 months", "last three months")) {
            plan.setTimeRange(QueryTimeRange.lastMonths(3));
        }
        if (containsAny(normalized, "闲置", "idle")) {
            plan.getFilters().setStatus("IDLE");
        }
        if (containsAny(normalized, "维修", "maintenance", "repair")) {
            plan.getFilters().setStatus("MAINTENANCE");
        }
        if (containsAny(normalized, "教学楼a", "building a")) {
            plan.getFilters().setLocation("教学楼A");
            addDimension(plan, QueryDimension.LOCATION);
        }
        if (containsAny(normalized, "资产总数", "total assets", "how many assets")) {
            plan.setMetric(QueryMetric.COUNT_ASSETS);
        }

        if (containsAny(normalized, "资产类别", "category")) {
            plan.setMetric(QueryMetric.CATEGORY_USAGE_RANKING);
            addDimension(plan, QueryDimension.CATEGORY);
            plan.setSort(new QuerySort("usageCount", "desc"));
            plan.setLimit(10);
        } else if (containsAny(normalized, "哪个部门", "部门使用", "department")) {
            plan.setMetric(QueryMetric.DEPARTMENT_USAGE_RANKING);
            addDimension(plan, QueryDimension.DEPARTMENT);
            plan.setSort(new QuerySort("usageCount", "desc"));
            plan.setLimit(10);
        } else if (containsAny(normalized, "使用次数最多", "most used", "top used")) {
            plan.setMetric(QueryMetric.TOP_USED_ASSETS);
            addDimension(plan, QueryDimension.ASSET);
            plan.setSort(new QuerySort("usageCount", "desc"));
            plan.setLimit(extractTopLimit(normalized));
        } else if (containsAny(normalized, "有哪些", "查询", "list", "which")) {
            addDimension(plan, QueryDimension.ASSET);
        }

        return plan;
    }

    private boolean containsAny(String text, String... candidates) {
        for (String candidate : candidates) {
            if (text.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    private void addDimension(QueryPlan plan, QueryDimension dimension) {
        if (!plan.getDimensions().contains(dimension)) {
            plan.getDimensions().add(dimension);
        }
    }

    private int extractTopLimit(String text) {
        if (text.contains("前10") || text.contains("top 10")) {
            return 10;
        }
        return 10;
    }
}
