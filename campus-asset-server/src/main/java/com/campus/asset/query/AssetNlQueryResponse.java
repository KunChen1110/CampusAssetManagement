package com.campus.asset.query;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AssetNlQueryResponse implements Serializable {

    private String question;
    private QueryPlan queryPlan;
    private String sqlPreview;
    private List<Map<String, Object>> result;
}
