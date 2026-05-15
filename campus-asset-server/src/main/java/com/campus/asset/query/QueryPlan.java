package com.campus.asset.query;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class QueryPlan implements Serializable {

    private QueryMetric metric;
    private List<QueryDimension> dimensions = new ArrayList<>();
    private QueryFilter filters = new QueryFilter();
    private QuerySort sort;
    private QueryTimeRange timeRange = QueryTimeRange.allTime();
    private Integer limit;
}
