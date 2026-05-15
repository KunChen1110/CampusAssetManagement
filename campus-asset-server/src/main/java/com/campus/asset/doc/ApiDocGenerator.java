package com.campus.asset.doc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ApiDocGenerator {

    public static void main(String[] args) throws IOException {
        Path output = Paths.get("docs/API.md");
        Files.createDirectories(output.getParent());
        Files.write(output, render().getBytes(StandardCharsets.UTF_8));
    }

    public static String render() {
        List<ApiMeta> apis = Arrays.asList(
                new ApiMeta("Asset Category", "POST", "/admin/asset-category", "AssetCategoryDTO", "Result<Void>", "Create an asset category"),
                new ApiMeta("Asset Category", "PUT", "/admin/asset-category", "AssetCategoryDTO", "Result<Void>", "Update an asset category"),
                new ApiMeta("Asset Category", "DELETE", "/admin/asset-category/{id}", "Path id", "Result<Void>", "Delete an asset category"),
                new ApiMeta("Asset Category", "GET", "/admin/asset-category/page", "AssetCategoryPageQueryDTO", "Result<PageResult<AssetCategory>>", "Page asset categories"),
                new ApiMeta("Asset Category", "GET", "/admin/asset-category/list", "AssetCategoryPageQueryDTO", "Result<List<AssetCategory>>", "List asset categories"),
                new ApiMeta("Asset", "POST", "/admin/asset", "AssetDTO", "Result<Void>", "Create an asset"),
                new ApiMeta("Asset", "PUT", "/admin/asset", "AssetDTO", "Result<Void>", "Update an asset"),
                new ApiMeta("Asset", "DELETE", "/admin/asset/{id}", "Path id", "Result<Void>", "Delete an asset"),
                new ApiMeta("Asset", "GET", "/admin/asset/page", "AssetPageQueryDTO", "Result<PageResult<Asset>>", "Page assets by multiple conditions"),
                new ApiMeta("Asset", "GET", "/admin/asset/{id}", "Path id", "Result<Asset>", "Get asset detail"),
                new ApiMeta("Asset", "PUT", "/admin/asset/{id}/status", "AssetStatusUpdateDTO", "Result<Void>", "Change asset status with lifecycle validation"),
                new ApiMeta("Asset Log", "GET", "/admin/asset-log/page", "AssetOperationLogPageQueryDTO", "Result<PageResult<AssetOperationLog>>", "Page asset operation logs"),
                new ApiMeta("Asset Usage", "POST", "/admin/asset-usage", "AssetUsageRecordDTO", "Result<Void>", "Create a usage record"),
                new ApiMeta("Asset Usage", "PUT", "/admin/asset-usage", "AssetUsageRecordDTO", "Result<Void>", "Update a usage record"),
                new ApiMeta("Asset Usage", "DELETE", "/admin/asset-usage/{id}", "Path id", "Result<Void>", "Delete a usage record"),
                new ApiMeta("Asset Usage", "GET", "/admin/asset-usage/page", "AssetUsageRecordPageQueryDTO", "Result<PageResult<AssetUsageRecord>>", "Page usage records"),
                new ApiMeta("Asset Statistics", "GET", "/admin/asset-statistics/summary", "-", "Result<AssetStatisticsSummaryVO>", "Asset dashboard summary"),
                new ApiMeta("Asset Statistics", "GET", "/admin/asset-statistics/top-used?limit=10", "limit", "Result<List<AssetUsageRankingVO>>", "Top used assets"),
                new ApiMeta("Asset Statistics", "GET", "/admin/asset-statistics/category-distribution", "-", "Result<List<AssetCategoryDistributionVO>>", "Asset counts grouped by category"),
                new ApiMeta("Asset Query", "POST", "/admin/asset-query/nl", "AssetNlQueryDTO", "Result<AssetNlQueryResponse>", "Rule-based controlled natural-language query")
        );

        StringBuilder builder = new StringBuilder();
        builder.append("# Campus Asset Management API\n\n");
        builder.append("| Module | Method | Endpoint | Request DTO | Response VO | Description |\n");
        builder.append("| --- | --- | --- | --- | --- | --- |\n");
        for (ApiMeta api : apis) {
            builder.append("| ")
                    .append(api.module).append(" | ")
                    .append(api.method).append(" | `")
                    .append(api.endpoint).append("` | `")
                    .append(api.request).append("` | `")
                    .append(api.response).append("` | ")
                    .append(api.description).append(" |\n");
        }
        return builder.toString();
    }

    private static class ApiMeta {
        private final String module;
        private final String method;
        private final String endpoint;
        private final String request;
        private final String response;
        private final String description;

        private ApiMeta(String module, String method, String endpoint, String request, String response, String description) {
            this.module = module;
            this.method = method;
            this.endpoint = endpoint;
            this.request = request;
            this.response = response;
            this.description = description;
        }
    }
}
