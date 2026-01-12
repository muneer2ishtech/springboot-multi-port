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
    "email": "muneer@example.com",
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
