<div align="center">

# Campus Asset Management

### Campus asset management and intelligent analysis backend built with Spring Boot

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

## Overview

This project is a backend system for campus asset management. It supports asset categories, asset inventory, usage records, lifecycle transitions, audit logs, statistics, and controlled natural-language queries.

The system follows a layered architecture: controllers expose admin APIs, services handle business rules and cache invalidation, MyBatis mappers access MySQL, Redis caches statistics, and JWT protects admin endpoints.

## Technical Highlights

- Controlled natural-language query: questions are parsed into a `QueryPlan`, then converted to SQL through whitelisted templates.
- Asset lifecycle management: status changes are validated by a centralized transition policy.
- Audit trail: create, update, delete, and status-change operations are persisted in `asset_operation_log`.
- Redis-backed statistics: summary and ranking data are cached for 10 minutes and invalidated on relevant mutations.
- Multi-module structure: common utilities, data objects, and server logic are separated for maintainability.

## Tech Stack

- Java, Spring Boot 2.7.3
- Spring MVC, Spring Cache, Spring Data Redis
- MyBatis, PageHelper
- MySQL
- Redis
- JWT authentication
- Knife4j / Swagger annotations
- Maven multi-module build

## Architecture

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

## Core Modules

- Asset Category: create, update, delete, page query, and list query.
- Asset: create, update, delete, detail query, page query, and status transition.
- Asset Usage Record: records asset user, department, usage time, purpose, and status.
- Asset Operation Log: records asset creation, updates, deletion, and lifecycle transitions.
- Asset Statistics: summary, top-used assets, and category distribution.
- Asset Query: converts Chinese or English questions into a safe `QueryPlan` and executes the query.
- API Documentation Generator: `com.campus.asset.doc.ApiDocGenerator` generates `docs/API.md`.

## Asset Lifecycle

Supported statuses:

- `IDLE`: available
- `ACTIVE`: currently in use
- `MAINTENANCE`: under maintenance
- `RETIRED`: retired from inventory

Allowed transitions:

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

Retired assets cannot be reactivated. Every status change is written to the operation log and can be queried through `/admin/asset-log/page`.

## Natural-Language QueryPlan Workflow

```text
semantic parsing -> QueryPlan -> SQL building -> query execution -> result formatting
```

The current implementation is rule-based and does not call a real LLM. Demo questions include:

- 资产总数是多少？
- 目前闲置资产有哪些？
- 使用次数最多的前10个资产是什么？
- 最近三个月使用次数最多的资产类别是什么？
- 哪个部门使用资产最多？
- 维修中的资产有多少？
- 查询教学楼A的闲置设备

The SQL builder only accepts whitelisted metrics:

- `COUNT_ASSETS`
- `TOP_USED_ASSETS`
- `CATEGORY_USAGE_RANKING`
- `DEPARTMENT_USAGE_RANKING`

Filters such as status, location, and department are bound as MyBatis parameters. Raw user input is not concatenated into executable SQL.

## Redis Cache

Statistics endpoints are cached for 10 minutes:

- `asset:statistics:summary`
- `asset:statistics:top-used:{limit}`
- `asset:statistics:category-distribution`

When asset category, asset, usage record, or asset status data changes, `asset:statistics:*` is invalidated.

## API Examples

Create an asset category:

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

Create an asset:

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

Change asset status:

```http
PUT /admin/asset/1/status
Content-Type: application/json

{
  "status": "MAINTENANCE",
  "remark": "Projector lens requires inspection"
}
```

Query operation logs:

```http
GET /admin/asset-log/page?assetId=1&page=1&pageSize=10
```

Natural-language query:

```http
POST /admin/asset-query/nl
Content-Type: application/json

{
  "question": "最近三个月使用次数最多的资产类别是什么？"
}
```

Response shape:

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

More endpoints: `docs/API.md`.

## Database

Create the database and run the schema before starting the backend:

```bash
mysql -uroot -p campus_asset < docs/campus_asset_schema.sql
```

Database configuration:

```text
campus-asset-server/src/main/resources/application-dev.yml
```

## How to Run

Prerequisites:

- MySQL on `localhost:3306`
- Redis on `localhost:6379`
- JDK and Maven

Build:

```bash
mvn clean package -pl campus-asset-server -am -DskipTests
```

Run:

```bash
mvn spring-boot:run -pl campus-asset-server
```

Default URL:

```text
http://localhost:8080
```

Knife4j API:

```text
http://localhost:8080/doc.html
```

Regenerate API Markdown:

```bash
mvn -q -pl campus-asset-server -am -DskipTests package
java -cp campus-asset-server/target/classes com.campus.asset.doc.ApiDocGenerator
```

## Future Improvements

- Add stricter request validation.
- Expand QueryPlan parsing and add an optional LLM adapter.
- Add Testcontainers-based integration tests for MySQL and Redis.
- Add role-based access control.
- Build a frontend management dashboard.
