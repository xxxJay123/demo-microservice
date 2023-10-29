# 演示微服务
[English Version](README.md)

演示微服务是一个基于微服务架构的应用程序，提供管理产品的功能。它包括基于角色的用户身份验证和授权，允许对API端点进行不同级别的访问。

## 先决条件

确保已安装以下工具和技术：

- **Java 17**
- **Maven**
- **MySQL 数据库**
- **Docker**
- **cURL**（用于API测试）

## 入门

### 安装

1. **Git clone:**

    ```sh
    git clone https://github.com/xxxJay123/demo-microservice.git
    cd demo-microservice
    ```

2. **使用 Maven 和 Docker 构建并运行应用程序:**

    ```sh
    mvn clean install
    docker-compose up
    ```

### 使用方法

#### **获取访问令牌:**

要获取用于身份验证的访问令牌，请使用以下 cURL 命令：
```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"yourUsername","password":"yourPassword"}' http://localhost:8080/auth/login
```
将 yourAccessToken 替换为从登录响应中获取的实际访问令牌。

#### **添加产品（需要 EDITOR 或 PRODUCT_ADMIN 角色）:**
要添加新产品，请使用以下 cURL 命令：
```bash
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer yourAccessToken" -d '{"name":"New Product","description":"Product Description"}' http://localhost:8080/products
```
将 yourAccessToken 替换为从登录响应中获取的实际访问令牌。

#### **更新产品（需要 EDITOR 或 PRODUCT_ADMIN 角色）:**
要更新现有产品，请使用以下 cURL 命令：
```bash
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer yourAccessToken" -d '{"name":"Updated Product","description":"Updated Description"}' http://localhost:8080/products/{productId}
```
将 {productId} 替换为要更新的产品的 ID，并将 yourAccessToken 替换为实际访问令牌。

#### **删除产品（需要 EDITOR 或 PRODUCT_ADMIN 角色）:**
要删除产品，请使用以下 cURL 命令：
```bash
curl -X DELETE -H "Authorization: Bearer yourAccessToken" http://localhost:8080/products/{productId}
```
将 {productId} 替换为要删除的产品的 ID，并将 yourAccessToken 替换为实际访问令牌。

### API 端点
- GET /products: 检索所有产品列表（USER 角色）
- GET /products/{id}: 按 ID 检索产品（USER 角色）
- POST /products: 创建新产品（EDITOR 和 PRODUCT_ADMIN 角色）
- PUT /products/{id}: 更新现有产品（EDITOR 和 PRODUCT_ADMIN 角色）
- DELETE /products/{id}: 删除产品（EDITOR 和 PRODUCT_ADMIN 角色）

### 身份验证和授权
- **USER 角色**：允许访问读取产品。
- **EDITOR 角色**：允许访问添加、更新和删除产品。
- **PRODUCT_ADMIN 角色**：具有 EDITOR 和 USER 角色的所有特权。
