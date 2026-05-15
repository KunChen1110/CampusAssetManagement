# 管理端 - 员工模块 API 文档

> 草稿版本，生成时间：2026-04-02
> 来源：`EmployeeController.java` + 相关 DTO/VO + `WebMvcConfiguration.java`

---

## 接口清单

| 接口名称       | 方法   | 路径                              | 是否鉴权 | 说明                     |
| -------------- | ------ | --------------------------------- | -------- | ------------------------ |
| 员工登录       | POST   | `/admin/employee/login`           | 否       | 管理端登录，返回 JWT     |
| 员工退出       | POST   | `/admin/employee/logout`          | 是       | 登出（服务端无状态）     |
| 新增员工       | POST   | `/admin/employee`                 | 是       | 添加一名新员工           |
| 员工分页查询   | GET    | `/admin/employee/page`            | 是       | 按姓名模糊搜索，分页返回 |
| 启用/禁用员工  | POST   | `/admin/employee/status/{status}` | 是       | 切换员工账号启用状态     |
| 查询员工信息   | GET    | `/admin/employee/{id}`            | 是       | 按 ID 查询单个员工       |
| 编辑员工信息   | PUT    | `/admin/employee`                 | 是       | 修改员工基本资料         |

> **鉴权说明**：除登录接口外，所有 `/admin/**` 请求须在请求头携带 `token` 字段（JWT 令牌），由 `JwtTokenAdminInterceptor` 验证。

---

## 接口详情

### 1. 员工登录

- 方法：`POST`
- 路径：`/admin/employee/login`
- 鉴权：`否`（已在拦截器 excludePathPatterns 中排除）
- 作用：员工使用用户名和密码登录，登录成功后返回 JWT 令牌。

#### 请求体

对应对象：`EmployeeLoginDTO`

| 字段       | 类型   | 必填 | 说明       |
| ---------- | ------ | ---- | ---------- |
| `username` | String | 是   | 员工账号   |
| `password` | String | 是   | 员工密码   |

示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

#### 响应结构

统一返回 `Result<EmployeeLoginVO>`：

```json
{
  "code": 1,
  "msg": null,
  "data": {
    "id": 1,
    "userName": "admin",
    "name": "管理员",
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

`data` 对应对象：`EmployeeLoginVO`

| 字段       | 类型   | 说明              |
| ---------- | ------ | ----------------- |
| `id`       | Long   | 员工 ID           |
| `userName` | String | 用户名            |
| `name`     | String | 员工姓名          |
| `token`    | String | JWT 令牌（后续请求携带至 `token` 请求头）|

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:46`
- DTO：`campus-asset-pojo/.../dto/EmployeeLoginDTO.java`
- VO：`campus-asset-pojo/.../vo/EmployeeLoginVO.java`
- Auth：`campus-asset-server/.../config/WebMvcConfiguration.java:47`

---

### 2. 员工退出

- 方法：`POST`
- 路径：`/admin/employee/logout`
- 鉴权：`是`（需携带 `token` 请求头）
- 作用：员工退出登录。服务端无状态，客户端自行清除本地 token 即可。

#### 请求参数

无

#### 响应结构

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:76`

---

### 3. 新增员工

- 方法：`POST`
- 路径：`/admin/employee`
- 鉴权：`是`
- 作用：由管理员创建一名新员工账号。

#### 请求体

对应对象：`EmployeeDTO`

| 字段         | 类型   | 必填 | 说明           |
| ------------ | ------ | ---- | -------------- |
| `username`   | String | 是   | 员工账号       |
| `name`       | String | 是   | 员工姓名       |
| `phone`      | String | 是   | 手机号         |
| `sex`        | String | 是   | 性别（`0` 女 / `1` 男）|
| `idNumber`   | String | 是   | 身份证号       |

> `id` 字段新增时不传，仅编辑时使用。

示例：

```json
{
  "username": "zhangsan",
  "name": "张三",
  "phone": "13800138000",
  "sex": "1",
  "idNumber": "110101199001011234"
}
```

#### 响应结构

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:87`
- DTO：`campus-asset-pojo/.../dto/EmployeeDTO.java`

---

### 4. 员工分页查询

- 方法：`GET`
- 路径：`/admin/employee/page`
- 鉴权：`是`
- 作用：按员工姓名模糊搜索，分页返回员工列表。

#### Query 参数

对应对象：`EmployeePageQueryDTO`

| 参数名       | 类型    | 必填 | 说明               |
| ------------ | ------- | ---- | ------------------ |
| `name`       | String  | 否   | 员工姓名（模糊匹配）|
| `page`       | int     | 是   | 页码（从 1 开始）  |
| `pageSize`   | int     | 是   | 每页记录数         |

#### 响应结构

统一返回 `Result<PageResult<Employee>>`：

```json
{
  "code": 1,
  "msg": null,
  "data": {
    "total": 10,
    "records": [
      {
        "id": 1,
        "username": "admin",
        "name": "管理员",
        "phone": "13800138000",
        "sex": "1",
        "idNumber": "110101199001011234",
        "status": 1,
        "createTime": "2023-01-01T10:00:00",
        "updateTime": "2023-06-01T12:00:00",
        "createUser": 1,
        "updateUser": 1
      }
    ]
  }
}
```

`data.records` 中每条记录对应 `Employee` 实体：

| 字段           | 类型          | 说明                        |
| -------------- | ------------- | --------------------------- |
| `id`           | Long          | 员工 ID                     |
| `username`     | String        | 账号                        |
| `name`         | String        | 姓名                        |
| `phone`        | String        | 手机号                      |
| `sex`          | String        | 性别                        |
| `idNumber`     | String        | 身份证号                    |
| `status`       | Integer       | 状态：`1` 启用 / `0` 禁用  |
| `createTime`   | LocalDateTime | 创建时间                    |
| `updateTime`   | LocalDateTime | 最后修改时间                |
| `createUser`   | Long          | 创建人 ID                   |
| `updateUser`   | Long          | 最后修改人 ID               |

> `password` 字段在 Entity 中存在，但接口响应中应避免返回，需确认 Service 层是否过滤（建议改用 VO 替代直接返回 Entity）。

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:96`
- DTO：`campus-asset-pojo/.../dto/EmployeePageQueryDTO.java`
- Entity：`campus-asset-pojo/.../entity/Employee.java`

---

### 5. 启用/禁用员工账号

- 方法：`POST`
- 路径：`/admin/employee/status/{status}`
- 鉴权：`是`
- 作用：切换指定员工账号的启用/禁用状态。

#### Path 参数

| 参数名   | 类型    | 必填 | 说明                   |
| -------- | ------- | ---- | ---------------------- |
| `status` | Integer | 是   | `1` 启用 / `0` 禁用    |

#### Query 参数

| 参数名 | 类型 | 必填 | 说明      |
| ------ | ---- | ---- | --------- |
| `id`   | Long | 是   | 员工 ID   |

示例请求：`POST /admin/employee/status/0?id=5`

#### 响应结构

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:104`

---

### 6. 查询员工信息

- 方法：`GET`
- 路径：`/admin/employee/{id}`
- 鉴权：`是`
- 作用：按员工 ID 查询单个员工的详细信息（用于编辑表单回显）。

#### Path 参数

| 参数名 | 类型 | 必填 | 说明    |
| ------ | ---- | ---- | ------- |
| `id`   | long | 是   | 员工 ID |

#### 响应结构

统一返回 `Result<Employee>`：

```json
{
  "code": 1,
  "msg": null,
  "data": {
    "id": 5,
    "username": "zhangsan",
    "name": "张三",
    "phone": "13800138000",
    "sex": "1",
    "idNumber": "110101199001011234",
    "status": 1,
    "createTime": "2023-01-01T10:00:00",
    "updateTime": "2023-06-01T12:00:00",
    "createUser": 1,
    "updateUser": 1
  }
}
```

`data` 字段同 [员工分页查询](#4-员工分页查询) 中的 `records` 条目。

> 同样存在直接返回 `Employee` 实体（含 `password` 字段）的风险，建议改用专用 VO。

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:112`
- Entity：`campus-asset-pojo/.../entity/Employee.java`

---

### 7. 编辑员工信息

- 方法：`PUT`
- 路径：`/admin/employee`
- 鉴权：`是`
- 作用：修改已有员工的基本资料。

#### 请求体

对应对象：`EmployeeDTO`

| 字段       | 类型   | 必填 | 说明                    |
| ---------- | ------ | ---- | ----------------------- |
| `id`       | Long   | 是   | 员工 ID（标识要修改哪条记录）|
| `username` | String | 是   | 账号                    |
| `name`     | String | 是   | 姓名                    |
| `phone`    | String | 是   | 手机号                  |
| `sex`      | String | 是   | 性别（`0` 女 / `1` 男）|
| `idNumber` | String | 是   | 身份证号                |

示例：

```json
{
  "id": 5,
  "username": "zhangsan",
  "name": "张三（改）",
  "phone": "13900139000",
  "sex": "1",
  "idNumber": "110101199001011234"
}
```

#### 响应结构

```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

#### 代码来源

- Controller：`campus-asset-server/.../controller/admin/EmployeeController.java:119`
- DTO：`campus-asset-pojo/.../dto/EmployeeDTO.java`

---

## 注意事项

1. **安全风险**：接口 4（分页查询）和接口 6（按 ID 查询）直接返回 `Employee` 实体，响应体中包含 `password` 字段。建议 Service 层将密码置空，或改用专用 VO。
2. **AOP 自动填充**：新增和编辑操作会由 `AutoFillAspect` 自动注入 `createTime`、`updateTime`、`createUser`、`updateUser`，无需客户端传入。
3. **示例值**：所有 JSON 示例均为`示例`，非接口实际返回的固定值。
