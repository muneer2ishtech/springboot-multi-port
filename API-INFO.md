#

- Check [Ports section in README.md](./README.md#ports) and use the correct port for the API calls.

## Swagger APIs

| Module  | Type    | HTTP  | URL              | Description |
|---------|-----------------|-------|------------------------------|-------------|
| API Doc | OpenAPI | GET   | /v3/api-docs     | Swagger generated API Documentation |
| API Doc | Swagger | GET   | /swagger-ui.html | Swagger Documentation Home          |


## Auth APIs
- For Authentication & Authorization APIs:
    - See [ishtech-springboot-jwtauth/API-INFO.md](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/main/API-INFO.md)


## Books APIs

| Module | Type              | HTTP   | URL                | Description |
|--------|-------------------|--------|--------------------|-------------|
| Book   | Create Book       | POST   | /api/v1/books      | Creates a new book and returns created book details |
| Book   | Get Book by ID    | GET    | /api/v1/books/{id} | Retrieves a book by its ID |
| Book   | Search/List Books | GET    | /api/v1/books      | Retrieves paginated list of books with filters |
| Book   | Update Book       | PUT    | /api/v1/books/{id} | Updates an existing book by ID |
| Book   | Delete Book       | DELETE | /api/v1/books/{id} | Deletes a book (returns 410 Gone) |


- For `curl` & `json` request/response samples:
    - See [CURL-INFO.md](./CURL-INFO.md)
