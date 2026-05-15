package com.campus.asset.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class QuerySqlBuilder {

    public BuiltQuery build(QueryPlan plan) {
        if (plan == null || plan.getMetric() == null) {
            throw new IllegalArgumentException("Query metric is required");
        }

        Map<String, Object> parameters = new LinkedHashMap<>();
        String sql;
        switch (plan.getMetric()) {
            case TOP_USED_ASSETS:
                sql = topUsedAssetsSql(plan, parameters);
                break;
            case CATEGORY_USAGE_RANKING:
                sql = categoryUsageRankingSql(plan, parameters);
                break;
            case DEPARTMENT_USAGE_RANKING:
                sql = departmentUsageRankingSql(plan, parameters);
                break;
            case COUNT_ASSETS:
                sql = countAssetsSql(plan, parameters);
                break;
            default:
                throw new IllegalArgumentException("Unsupported query metric");
        }

        return new BuiltQuery(sql, toMyBatisSql(sql), parameters);
    }

    private String countAssetsSql(QueryPlan plan, Map<String, Object> parameters) {
        String select = plan.getDimensions().contains(QueryDimension.ASSET)
                ? "select id, asset_code as assetCode, name, location, owner_department as ownerDepartment, status from asset"
                : "select count(*) as totalAssets from asset";
        return select + assetWhereClause(plan, parameters);
    }

    private String topUsedAssetsSql(QueryPlan plan, Map<String, Object> parameters) {
        putLimit(plan, parameters);
        return "select a.id as assetId, a.asset_code as assetCode, a.name as assetName, count(r.id) as usageCount " +
                "from asset_usage_record r inner join asset a on r.asset_id = a.id" +
                usageWhereClause(plan, parameters) +
                " group by a.id, a.asset_code, a.name order by usageCount desc limit #{limit}";
    }

    private String categoryUsageRankingSql(QueryPlan plan, Map<String, Object> parameters) {
        putLimit(plan, parameters);
        return "select c.id as categoryId, c.name as categoryName, count(r.id) as usageCount " +
                "from asset_usage_record r inner join asset a on r.asset_id = a.id " +
                "inner join asset_category c on a.category_id = c.id" +
                usageWhereClause(plan, parameters) +
                " group by c.id, c.name order by usageCount desc limit #{limit}";
    }

    private String departmentUsageRankingSql(QueryPlan plan, Map<String, Object> parameters) {
        putLimit(plan, parameters);
        return "select r.department as department, count(r.id) as usageCount " +
                "from asset_usage_record r" +
                usageWhereClause(plan, parameters) +
                " group by r.department order by usageCount desc limit #{limit}";
    }

    private String assetWhereClause(QueryPlan plan, Map<String, Object> parameters) {
        StringBuilder where = new StringBuilder(" where 1 = 1");
        QueryFilter filters = plan.getFilters();
        if (filters.getStatus() != null) {
            where.append(" and status = #{status}");
            parameters.put("status", filters.getStatus());
        }
        if (filters.getLocation() != null) {
            where.append(" and location = #{location}");
            parameters.put("location", filters.getLocation());
        }
        if (filters.getDepartment() != null) {
            where.append(" and owner_department = #{department}");
            parameters.put("department", filters.getDepartment());
        }
        return where.toString();
    }

    private String usageWhereClause(QueryPlan plan, Map<String, Object> parameters) {
        StringBuilder where = new StringBuilder(" where 1 = 1");
        if (plan.getTimeRange() != null && QueryTimeRange.Type.LAST_MONTHS.equals(plan.getTimeRange().getType())) {
            int months = plan.getTimeRange().getAmount() == null ? 3 : plan.getTimeRange().getAmount();
            where.append(" and r.usage_start_time >= #{beginTime}");
            parameters.put("beginTime", LocalDateTime.now().minusMonths(months));
        }
        QueryFilter filters = plan.getFilters();
        if (filters.getDepartment() != null) {
            where.append(" and r.department = #{department}");
            parameters.put("department", filters.getDepartment());
        }
        return where.toString();
    }

    private void putLimit(QueryPlan plan, Map<String, Object> parameters) {
        int limit = plan.getLimit() == null || plan.getLimit() <= 0 ? 10 : Math.min(plan.getLimit(), 100);
        parameters.put("limit", limit);
    }

    private String toMyBatisSql(String sql) {
        String result = sql;
        result = result.replace("#{status}", "#{query.parameters.status}");
        result = result.replace("#{location}", "#{query.parameters.location}");
        result = result.replace("#{department}", "#{query.parameters.department}");
        result = result.replace("#{beginTime}", "#{query.parameters.beginTime}");
        result = result.replace("#{limit}", "#{query.parameters.limit}");
        return result;
    }

    @Data
    @AllArgsConstructor
    public static class BuiltQuery implements Serializable {
        private String sql;
        private String myBatisSql;
        private Map<String, Object> parameters;
    }
}
