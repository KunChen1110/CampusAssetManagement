<div align="center">

# 校园资产管理与智能分析系统

### 基于 Spring Boot 的校园资产管理与智能分析后端系统

[English](README.md) | [简体中文](README.zh-CN.md)

![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.3-6DB33F?style=flat-square)
![MyBatis](https://img.shields.io/badge/MyBatis-ORM-BB1B1B?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat-square)
![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?style=flat-square)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-Multi--Module-C71A36?style=flat-square)

</div>

---

## 项目简介

本项目面向校园资产管理场景，支持资产分类、资产台账、使用记录、状态流转、操作审计、统计分析和自然语言查询。

系统采用经典分层架构：Controller 负责接口入口，Service 处理业务规则和缓存失效，Mapper 通过 MyBatis 访问 MySQL，Redis 用于缓存统计结果，JWT 用于管理端接口鉴权。

## 技术亮点

- 受控自然语言查询：用户问题先解析为 `QueryPlan`，再由白名单 SQL 构建器生成查询语句。
- 资产生命周期管理：资产状态变更由统一策略校验，避免任意状态跳转。
- 操作审计：资产创建、更新、删除和状态变更都会写入 `asset_operation_log`。
- Redis 统计缓存：统计摘要和排行榜结果缓存 10 分钟，并在相关数据变化后失效。
- 多模块结构：公共组件、数据对象和业务服务分离，便于维护和扩展。

## 技术栈

- Java, Spring Boot 2.7.3
- Spring MVC, Spring Cache, Spring Data Redis
- MyBatis, PageHelper
- MySQL
- Redis
- JWT authentication
- Knife4j / Swagger annotations
- Maven multi-module build

## 架构

```text
campus-asset-common
  Result, PageResult, exceptions, JWT utilities, Redis/Jackson helpers

campus-asset-pojo
  Entity, DTO, and VO classes

campus-asset-server
  Controller -> Service -> Mapper -> MySQL
  Redis-backed statistics cache
  Rule-based QueryPlan natural-language query module
```

## 核心模块

- 资产分类：新增、修改、删除、分页查询、列表查询。
- 资产台账：新增、修改、删除、详情、分页查询、状态流转。
- 使用记录：记录资产使用人、部门、使用时间、用途和状态。
- 操作日志：记录资产创建、更新、删除和生命周期变更。
- 统计分析：资产总览、高频使用资产、分类分布。
- 自然语言查询：将中文或英文问题转换为安全的 `QueryPlan` 并执行查询。
- API 文档生成：`com.campus.asset.doc.ApiDocGenerator` 生成 `docs/API.md`。

## 资产生命周期

支持的资产状态：

- `IDLE`：闲置，可借用或分配
- `ACTIVE`：使用中
- `MAINTENANCE`：维修中
- `RETIRED`：已退役

允许的状态流转：

```text
IDLE -> ACTIVE
IDLE -> MAINTENANCE
IDLE -> RETIRED
ACTIVE -> IDLE
ACTIVE -> MAINTENANCE
ACTIVE -> RETIRED
MAINTENANCE -> IDLE
MAINTENANCE -> RETIRED
```

`RETIRED` 状态的资产不能重新启用。所有状态变更都会写入操作日志，可通过 `/admin/asset-log/page` 查询。

## 自然语言查询流程

```text
语义解析 -> 查询计划 -> SQL 构建 -> 查询执行 -> 结果格式化
```

当前实现是规则解析，不调用真实 LLM。支持示例问题：

- 资产总数是多少？
- 目前闲置资产有哪些？
- 使用次数最多的前10个资产是什么？
- 最近三个月使用次数最多的资产类别是什么？
- 哪个部门使用资产最多？
- 维修中的资产有多少？
- 查询教学楼A的闲置设备

SQL 构建器只接受白名单指标：

- `COUNT_ASSETS`
- `TOP_USED_ASSETS`
- `CATEGORY_USAGE_RANKING`
- `DEPARTMENT_USAGE_RANKING`

状态、地点、部门等过滤条件通过 MyBatis 参数绑定，不直接拼接用户输入。

## Redis 缓存

统计接口缓存 10 分钟：

- `asset:statistics:summary`
- `asset:statistics:top-used:{limit}`
- `asset:statistics:category-distribution`

资产分类、资产、使用记录和资产状态发生变化时，会清理 `asset:statistics:*`。

## API 示例

新增资产分类：

```http
POST /admin/asset-category
Content-Type: application/json

{
  "name": "Teaching Equipment",
  "type": 1,
  "description": "Classroom and lab equipment",
  "status": 1
}
```

新增资产：

```http
POST /admin/asset
Content-Type: application/json

{
  "assetCode": "CAM-A-001",
  "name": "Projector A-101",
  "categoryId": 1,
  "location": "教学楼A",
  "ownerDepartment": "Academic Affairs",
  "status": "IDLE",
  "purchaseDate": "2025-09-01",
  "originalValue": 4500.00
}
```

变更资产状态：

```http
PUT /admin/asset/1/status
Content-Type: application/json

{
  "status": "MAINTENANCE",
  "remark": "Projector lens requires inspection"
}
```

查询操作日志：

```http
GET /admin/asset-log/page?assetId=1&page=1&pageSize=10
```

自然语言查询：

```http
POST /admin/asset-query/nl
Content-Type: application/json

{
  "question": "最近三个月使用次数最多的资产类别是什么？"
}
```

响应结构：

```json
{
  "code": 1,
  "data": {
    "question": "最近三个月使用次数最多的资产类别是什么？",
    "queryPlan": {
      "metric": "CATEGORY_USAGE_RANKING",
      "dimensions": ["CATEGORY"],
      "filters": {},
      "sort": {"field": "usageCount", "direction": "desc"},
      "timeRange": {"type": "LAST_MONTHS", "amount": 3},
      "limit": 10
    },
    "sqlPreview": "select ... where r.usage_start_time >= #{beginTime} ...",
    "result": []
  }
}
```

更多接口见：`docs/API.md`。

## 数据库

启动前先创建数据库并执行 schema：

```bash
mysql -uroot -p campus_asset < docs/campus_asset_schema.sql
```

数据库连接配置位于：

```text
campus-asset-server/src/main/resources/application-dev.yml
```

## 运行方式

环境要求：

- MySQL on `localhost:3306`
- Redis on `localhost:6379`
- JDK and Maven

构建：

```bash
mvn clean package -pl campus-asset-server -am -DskipTests
```

启动：

```bash
mvn spring-boot:run -pl campus-asset-server
```

默认地址：

```text
http://localhost:8080
```

Knife4j API：

```text
http://localhost:8080/doc.html
```

重新生成 API Markdown：

```bash
mvn -q -pl campus-asset-server -am -DskipTests package
java -cp campus-asset-server/target/classes com.campus.asset.doc.ApiDocGenerator
```

## 后续计划

- 增加更完整的请求参数校验。
- 扩展 QueryPlan 解析能力，并预留 LLM Adapter。
- 使用 Testcontainers 增加 MySQL 和 Redis 集成测试。
- 增加基于角色的权限控制。
- 增加前端管理界面和统计看板。
