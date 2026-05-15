package com.campus.asset.query;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QueryResultFormatter {

    public List<Map<String, Object>> format(List<Map<String, Object>> rows) {
        return rows;
    }
}
