# springboot-multi-port
Spring Boot application that can run on multiple ports at the same time

## Tech stack
- Java: 25
- Spring Boot: 4.0.x
- Database: H2
- Containerization: Docker

##

[GIT](https://github.com/muneer2ishtech/springboot-multi-port)


## Design
- [ishtech-jpa-base](https://github.com/ishtech/ishtech-base-jpa) - Foundational JPA and other base classes
- [ishtech-springboot-jwtauth](https://github.com/ishtech/ishtech-springboot-jwtauth) - For Authentication & Authorization

## Ports

- Default port: 8080

### Additional Ports
- If application property `fi.ishtech.practice.springboot.multiport.additional-ports` or environment variable `FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL-PORTS` is set to `true`
    - PORT for `**/users/**` is `8082`
    - PORT for `**/books/**` is `8081`
- By default additional-ports is set to `false`, i.e. all API URLs use only `8080` port


## APIs

- For details you can see swagger documentation
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
    - [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3//v3/api-docs.yaml)

- Note: Check and update URI and PORT on which application is running

- For API names and descriptions:
    - See [API-INFO.md](./API-INFO.md)

- For `curl` & `json` request/response samples:
    - See [CURL-INFO.md](./CURL-INFO.md)


## DB

### Local

- [http://localhost:8080/h2-console](http://localhost:8080/h2-console)


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
    - With default `8081` for books APIs, `8082` for user APIs

```
./mvnw spring-boot:run -Dspring-boot.run.arguments="--fi.ishtech.practice.springboot.multiport.additional-ports=true"
```

- To run on multiple ports / use additional ports
    - To use custom ports for books and user APIs

```
./mvnw spring-boot:run \
  -Dspring-boot.run.arguments="\
    --fi.ishtech.practice.springboot.multiport.additional-ports=true \
    --fi.ishtech.practice.springboot.multiport.user-port=8282 \
    --fi.ishtech.practice.springboot.multiport.book-port=8181"
```

### Docker

- See [DOCKER-BUILD.md](./DOCKER-BUILD.md)
