
## How to
See [How-to](./HOW-TO.md) on how to make maven, docker builds and push & pull images to Docker Hub

### Docker Image pulled from Docker Hub
- [How to run Docker Image pulled from Docker Hub](./HOW-TO.md#run-docker-image-pulled-from-docker-hub) without making any builds locally

# Ports

- Default port: 8080

### Additional Ports
- If application property `fi.ishtech.practice.springboot.multiport.additional-ports` or environment variable `FI_ISHTECH_CODINGEXERCISE_MULTIPORT_ADDITIONAL-PORTS` is set to `true`
    - PORT for `**/users/**` is `8082`
    - PORT for `**/books/**` is `8081`
- By default additional-ports is set to `false`, i.e. all API URLs use only `8080` port

# authentication & Autherization

- Uses [ishtech-springboot-jwtauth](https://github.com/ishtech/ishtech-springboot-jwtauth)


# APIs

|Module |API                 |HTTP   |URL                                     |
|-------|--------------------|-------|----------------------------------------|
|User   |Get User Details    |GET    |localhost:PORT/api/v1/users/{userId}    |
|User   |Update User Details |PUT    |localhost:PORT/api/v1/users             |
|       |                    |       |                                        |
|Book   |Create Book         |POST   |localhost:PORT/api/v1/books             |
|Book   |Get All Books       |GET    |localhost:PORT/api/v1/books             |
|Book   |Update Book         |PUT    |localhost:PORT/api/v1/books             |
|Book   |Get Book by ID      |GET    |localhost:PORT/api/v1/books/{bookId}    |
|Book   |Delete Book By ID   |DELETE |localhost:PORT/api/v1/books/{bookId}    |
|       |                    |       |                                        |
|API Doc| OpenAPI            | GET   |localhost:8080/api-docs                 |
|API Doc| Swagger            | GET   |localhost:8080/swagger-ui.html          |


# API Details 

## APIs for Auth
- See [Auth APIs Info](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/main/API-INFO.md)

## APIs for USER management

<table>
<tr>
<td><b>Get User Details</b> - <code>HTTP GET</code></td>
<td><a href="http://localhost:8080/api/v1/users/{userId}">localhost:PORT/api/v1/users/1</a></td>
</tr>
<tr>
<td>
Request Param: <code>userId</code><br><br>
If <code>userId</code> does not match with <code>user.id</code> in authentication context,<br>
then <code>403-Forbidden</code>error will be thrown.

</td>
<td style="vertical-align:text-top">
Sample Response JSON

```json
{
    "id": 1,
    "email": "muneer2ishtech@gmail.com",
    "firstName": "Muneer",
    "lastName": "Syed"
}
```
</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Update User Details</b> - <code>HTTP PUT</code></td>
<td><a href="http://localhost:8080/api/v1/users">localhost:PORT/api/v1/users</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Sample Request JSON

```json
{
    "id": 1,
    "firstName": "New Muneer",
    "lastName": "New Syed"
}
```

If <code>user.id</code> from request body does not match with <code>user.id</code> in authentication context,<br>
then <code>403-Forbidden</code> error will be thrown.<br><br>
email or password cannot be updated using this request

</td>
<td style="vertical-align:text-top">
Sample Response JSON

```json
{
    "id": 1,
    "firstName": "New Muneer",
    "lastName": "New Syed"
}
```
</td>
</tr>
</table>
<br>
<br>
<br>

## APIs for BOOK management

<table>
<tr>
<td><b>Create Book</b> - <code>HTTP POST</code></td>
<td><a href="http://localhost:8080/api/v1/books">localhost:PORT/api/v1/books</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Sample Request JSON

```json
{
    "title" : "Postgre Fundamentals",
    "author" : "Ahmed",
    "year": 2022,
    "price": 12.34
}
```

</td>
<td>
Http Response code is 201 - Created<br>
Response is <code>id</code> of the book created<br><br>
Http Reponse code - 400 - Bad Request if Book name already exists
</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Update Book</b> - <code>HTTP PUT</code></td>
<td><a href="http://localhost:8080/api/v1/books">localhost:PORT/api/v1/books</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Sample Request JSON

```json
{
    "id": 1,
    "title" : "Postgre Fundamentals - New Edition",
    "author" : "Ahmed",
    "year": 2023,
    "price": 12.35
}
```

</td>
<td style="vertical-align:text-top">
Sample Response JSON

```json
{
    "id": 1,
    "title": "Postgre Fundamentals - New Edition",
    "author": "Ahmed",
    "year": 2023,
    "price": 12.35
}
```
</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Get All Books</b> - <code>HTTP GET</code></td>
<td><a href="http://localhost:8080/api/v1/books">localhost:PORT/api/v1/books</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
No Request Body
</td>
<td style="vertical-align:text-top">
Sample Response JSON

```json
[
    {
        "id": 1,
        "title": "Postgre Fundamentals - New Edition",
        "author": "Ahmed",
        "year": 2023,
        "price": 12.35
    },
    {
        "id": 1,
        "title": "Java Intro",
        "author": "Ahmed 2",
        "year": 2024,
        "price": 56,78
    }
]
```
</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Get Book by ID</b> - <code>HTTP GET</code></td>
<td><a href="http://localhost:8080/api/v1/books/{bookId}">localhost:PORT/api/v1/books/{bookId}</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Request Param: <code>bookId</code>
</td>
<td style="vertical-align:text-top">
Sample Response JSON

```json
{
	"id": 1,
	"title": "Postgre Fundamentals - New Edition",
	"author": "Ahmed",
	"year": 2023,
	"price": 12.35
}
```
Http Reponse code <code>404 - NOT_FOUND</code> - If no book exists by given id

</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Delete Book by ID</b> - <code>HTTP DELETE</code></td>
<td><a href="http://localhost:8080/api/v1/books/{bookId}">localhost:PORT/api/v1/books/{bookId}</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Request Param: <code>bookId</code>
</td>
<td style="vertical-align:text-top">
Http Reponse code <code>410 - GONE</code>  - for successfully deleting book in DB<br><br>
Http Reponse code <code>404 - NOT_FOUND</code> - If no book exists by given id

</td>
</tr>
</table>
<br>
<br>
<br>
