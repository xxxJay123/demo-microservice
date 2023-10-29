# Demo Microservice

Demo Microservice is a microservice-based application that provides functionality to manage products. It includes user authentication and authorization based on roles, allowing different levels of access to the API endpoints.

[中文版本](README_zh.md)
## Prerequisites

Ensure you have the following tools and technologies installed:

- **Java 17**
- **Maven**
- **MySQL Database**
- **Docker**
- **cURL** (for API testing)

## Getting Started

### Installation

1. **Clone the repository:**

    ```sh
    git clone https://github.com/xxxJay123/demo-microservice.git
    cd demo-microservice
    ```

2. **Build and run the application using Maven and Docker:**

    ```sh
    mvn clean install
    docker-compose up
    ```

### Usage

#### **Getting Access Token:**

To obtain an access token for authentication, use the following cURL command:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"yourUsername","password":"yourPassword"}' http://localhost:8080/auth/login
```

#### **Adding a Product (Requires EDITOR or PRODUCT_ADMIN Role):**
To add a new product, use the following cURL command:
```bash
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer yourAccessToken" -d '{"name":"New Product","description":"Product Description"}' http://localhost:8080/products
```
Replace yourAccessToken with the actual access token obtained from the login response.

#### **Updating a Product (Requires EDITOR or PRODUCT_ADMIN Role):**
To update an existing product, use the following cURL command:
```bash
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer yourAccessToken" -d '{"name":"Updated Product","description":"Updated Description"}' http://localhost:8080/products/{productId}
```
Replace {productId} with the ID of the product you want to update and yourAccessToken with the actual access token.

#### **Deleting a Product (Requires EDITOR or PRODUCT_ADMIN Role):**
To delete a product, use the following cURL command:
```bash
curl -X DELETE -H "Authorization: Bearer yourAccessToken" http://localhost:8080/products/{productId}
```
Replace {productId} with the ID of the product you want to delete and yourAccessToken with the actual access token.

### API Endpoint
- GET /products: Retrieve a list of all products. (USER Role)
- GET /products/{id}: Retrieve a product by ID. (USER Role)
- POST /products: Create a new product. (EDITOR and PRODUCT_ADMIN Role)
- PUT /products/{id}: Update an existing product. (EDITOR and PRODUCT_ADMIN Role)
- DELETE /products/{id}: Delete a product. (EDITOR and PRODUCT_ADMIN Role)

### Authentication and Authorization
- **USER Role**: Allows access to read products.
- **EDITOR Role**: Allows access to add, update, and delete products.
- **PRODUCT_ADMIN Role**: Has all the privileges of EDITOR and USER roles.
