# Campus Asset Management API

| Module | Method | Endpoint | Request DTO | Response VO | Description |
| --- | --- | --- | --- | --- | --- |
| Asset Category | POST | `/admin/asset-category` | `AssetCategoryDTO` | `Result<Void>` | Create an asset category |
| Asset Category | PUT | `/admin/asset-category` | `AssetCategoryDTO` | `Result<Void>` | Update an asset category |
| Asset Category | DELETE | `/admin/asset-category/{id}` | `Path id` | `Result<Void>` | Delete an asset category |
| Asset Category | GET | `/admin/asset-category/page` | `AssetCategoryPageQueryDTO` | `Result<PageResult<AssetCategory>>` | Page asset categories |
| Asset Category | GET | `/admin/asset-category/list` | `AssetCategoryPageQueryDTO` | `Result<List<AssetCategory>>` | List asset categories |
| Asset | POST | `/admin/asset` | `AssetDTO` | `Result<Void>` | Create an asset |
| Asset | PUT | `/admin/asset` | `AssetDTO` | `Result<Void>` | Update an asset |
| Asset | DELETE | `/admin/asset/{id}` | `Path id` | `Result<Void>` | Delete an asset |
| Asset | GET | `/admin/asset/page` | `AssetPageQueryDTO` | `Result<PageResult<Asset>>` | Page assets by multiple conditions |
| Asset | GET | `/admin/asset/{id}` | `Path id` | `Result<Asset>` | Get asset detail |
| Asset | PUT | `/admin/asset/{id}/status` | `AssetStatusUpdateDTO` | `Result<Void>` | Change asset status with lifecycle validation |
| Asset Log | GET | `/admin/asset-log/page` | `AssetOperationLogPageQueryDTO` | `Result<PageResult<AssetOperationLog>>` | Page asset operation logs |
| Asset Usage | POST | `/admin/asset-usage` | `AssetUsageRecordDTO` | `Result<Void>` | Create a usage record |
| Asset Usage | PUT | `/admin/asset-usage` | `AssetUsageRecordDTO` | `Result<Void>` | Update a usage record |
| Asset Usage | DELETE | `/admin/asset-usage/{id}` | `Path id` | `Result<Void>` | Delete a usage record |
| Asset Usage | GET | `/admin/asset-usage/page` | `AssetUsageRecordPageQueryDTO` | `Result<PageResult<AssetUsageRecord>>` | Page usage records |
| Asset Statistics | GET | `/admin/asset-statistics/summary` | `-` | `Result<AssetStatisticsSummaryVO>` | Asset dashboard summary |
| Asset Statistics | GET | `/admin/asset-statistics/top-used?limit=10` | `limit` | `Result<List<AssetUsageRankingVO>>` | Top used assets |
| Asset Statistics | GET | `/admin/asset-statistics/category-distribution` | `-` | `Result<List<AssetCategoryDistributionVO>>` | Asset counts grouped by category |
| Asset Query | POST | `/admin/asset-query/nl` | `AssetNlQueryDTO` | `Result<AssetNlQueryResponse>` | Rule-based controlled natural-language query |
