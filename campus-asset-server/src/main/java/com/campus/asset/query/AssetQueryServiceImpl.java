package com.campus.asset.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AssetQueryServiceImpl implements AssetQueryService {

    @Autowired
    private QueryPlanParser queryPlanParser;

    @Autowired
    private QuerySqlBuilder querySqlBuilder;

    @Autowired
    private QueryExecutionService queryExecutionService;

    @Autowired
    private QueryResultFormatter queryResultFormatter;

    @Override
    public AssetNlQueryResponse query(String question) {
        QueryPlan queryPlan = queryPlanParser.parse(question);
        QuerySqlBuilder.BuiltQuery builtQuery = querySqlBuilder.build(queryPlan);
        List<Map<String, Object>> rows = queryExecutionService.execute(builtQuery);
        return AssetNlQueryResponse.builder()
                .question(question)
                .queryPlan(queryPlan)
                .sqlPreview(builtQuery.getSql())
                .result(queryResultFormatter.format(rows))
                .build();
    }
}
