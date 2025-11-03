
## Build
### Maven build
```
./mvnw clean install -DskipTests=true
```
or

```
./mvnw clean package
```

- Note: check and use version from pom.xml

## Local Run
### Run using Maven

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
    --fi.ishtech.practice.springboot.multiport.user-port=8082 \
    --fi.ishtech.practice.springboot.multiport.book-port=8081"
```

### To Run using Docker

- Add option ` -d` if you want to run in background


- To run on single port / without additional ports
    - default port `8080`

```
docker run muneer2ishtech/ishtechspringboot-multiport:1.0.0
```

- To run on multiple ports / use additional ports
    - With default `8081` for books URLs, `8082` for user URLs

```
docker run -d \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL-PORTS=true \
  muneer2ishtech/ishtechspringboot-multiport:1.0.0
```

- To run on multiple ports / use additional ports
    - To use custom ports for books and user URLs

```
docker run -d \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL-PORTS=true \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK-PORT=8081 \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER-PORT=8082 \
  muneer2ishtech/ishtechspringboot-multiport:1.0.0
```

## APIs
See [APIs](./README.md#APIs) on how to find APIs to use for application
