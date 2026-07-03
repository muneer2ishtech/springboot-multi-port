
## Docker

### Docker build

```
docker build . \
  -t "muneer2ishtech/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout 2>/dev/null):$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null)"
```

### Run with docker image

- Note: check and use version from pom.xml
- Add option ` -d` if you want to run in background


- To run on single port / without additional ports

```
docker run -p 8080:8080 muneer2ishtech/ishtech-springboot-multi-port:x.y.z
```

- To run on multiple ports / use additional ports
    - With default `8081` for books APIs, `8082` for user APIs

```
docker run \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS=true \
  -p 8080:8080 \
  -p 8081:8081 \
  -p 8082:8082 \
  muneer2ishtech/ishtech-springboot-multi-port:x.y.z
```

- To run on multiple ports / use additional ports
    - To use custom ports for books and user APIs

```
docker run \
  -e FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS=true \
  -p 8180:8080 \
  -p 8181:8081 \
  -p 8182:8082 \
  muneer2ishtech/ishtech-springboot-multi-port:x.y.z
```

### Run with docker compose

- Docker compose is self contained, so  you don't need anything else other than docker

- To stop if running
    - `docker compose stop`

- To stop and remove including volumes and built images
    - `docker compose down -v --rmi=local`

- To build and start with default settings

```
docker compose up --build

```

- To build and start with custom settings
    - You can prefix with env vars as in below example
    - Below args are optional, you can change to desired value or skip, if skipped they will use default value
        - `SERVER_PORT` if skipped spring-boot app will run on default `8080` inside container
        - `SERVER_PORT_LOCAL` if skipped spring-boot app will be exposed on host using `SERVER_PORT` value
        - `FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT` if skipped books APIs will be exposed on default `8081`
        - `FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT` if skipped user APIs will be exposed on default `8082`
        - `FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS` if skipped defaults to `false`

```
SERVER_PORT=8180 \
FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS=true \
FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT=8181 \
FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT=8182 \
APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
docker compose up --build

```
