# springboot-multi-port
To run single spring-boot application on multiple ports at the same time

## Tech stack
- Java: 25
- Spring Boot: 3.5.7

##

[GIT](https://github.com/muneer2ishtech/springboot-multi-port)


## Ports

- Default port: 8080

### Additional Ports
- If application property `fi.ishtech.practice.springboot.multiport.additional-ports` or environment variable `FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL-PORTS` is set to `true`
    - PORT for `**/users/**` is `8082`
    - PORT for `**/books/**` is `8081`
- By default additional-ports is set to `false`, i.e. all API URLs use only `8080` port


## APIs

| Module  | Type                | HTTP   | URL                                      |
|---------|---------------------|--------|------------------------------------------|
| API Doc | OpenAPI             | GET    | localhost:8080/api-docs                  |
| API Doc | Swagger             | GET    | localhost:8080/swagger-ui.html           |
| User    | Get User Details    | GET    | localhost:PORT1/api/v1/users/{userId}    |
| User    | Update User Details | PUT    | localhost:PORT1/api/v1/users             |
| Book    | Create Book         | POST   | localhost:PORT2/api/v1/books             |
| Book    | Get All Books       | GET    | localhost:PORT2/api/v1/books             |
| Book    | Update Book         | PUT    | localhost:PORT2/api/v1/books             |
| Book    | Get Book by ID      | GET    | localhost:PORT2/api/v1/books/{bookId}    |
| Book    | Delete Book By ID   | DELETE | localhost:PORT2/api/v1/books/{bookId}    |

- For details you can see swagger documentation
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- Note: Check and update URI and PORT on which application is running

- For API request/response samples:
    - See [API-INFO.md](./API-INFO.md)

- For Authentiation & Authorization APIs:
    - See [ishtech-springboot-jwtauth](https://github.com/ishtech/ishtech-springboot-jwtauth)


## Build and Run

- Ensure the port, db properties etc are correct in application-xxx.properties

### Maven

#### Local Maven Build

- Build without tests

```
./mvnw clean install -DskipTests
```

- Build with Junit tests

```
./mvnw clean install
```

#### Local Maven Run

- To run on single port / without additional ports

```
./mvnw spring-boot:run
```

- To run on multiple ports / use additional ports
    - With default `8081` for books URLs, `8082` for user URLs

```
./mvnw spring-boot:run -Dspring-boot.run.arguments="--fi.ishtech.practice.springboot.multiport.additional-ports=true"
```

- To run on multiple ports / use additional ports
    - To use custom ports for books and user URLs

```
./mvnw spring-boot:run \
  -Dspring-boot.run.arguments="\
    --fi.ishtech.practice.springboot.multiport.additional-ports=true \
    --fi.ishtech.practice.springboot.multiport.user-port=8282 \
    --fi.ishtech.practice.springboot.multiport.book-port=8181"
```



### Docker

#### Docker build

```
docker build . \
  -t "muneer2ishtech/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout 2>/dev/null):$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null)"
```

#### Run using docker image

- Note: check and use version from pom.xml
- Add option ` -d` if you want to run in background


- To run on single port / without additional ports
    - default port `8080`

```
docker run \
  muneer2ishtech/ishtech-springboot-multi-port:x.y.z
```

- To run on multiple ports / use additional ports
    - With default `8081` for books URLs, `8082` for user URLs

```
docker run \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL-PORTS=true \
  muneer2ishtech/ishtech-springboot-multi-port:x.y.z
```

- To run on multiple ports / use additional ports
    - To use custom ports for books and user URLs

```
docker run \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL-PORTS=true \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK-PORT=8181 \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER-PORT=8282 \
  muneer2ishtech/ishtech-springboot-multi-port:x.y.z
```
