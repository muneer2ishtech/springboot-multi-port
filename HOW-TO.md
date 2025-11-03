
## Build
### Maven build
```
mvn clean install -DskipTests=true
```
or

```
mvn clean package
```

### Docker build
```
docker build -f Dockerfile . -t muneer2ishtech/springboot_multiport_coding_exercise:1.0.0
```

#### Docker build multiple tags
```
docker build -f Dockerfile . -t muneer2ishtech/springboot_multiport_coding_exercise:1.0.0 -t muneer2ishtech/springboot_multiport_coding_exercise:latest
```

## Local Run
### Run using Maven
- Without additional ports

```
mvn spring-boot:run
```

- With additional ports

```
mvn spring-boot:run -Dspring-boot.run.arguments="--fi.ishtech.practice.springboot.multiport.additional-ports=true"
```

### Run using already built Docker image
```
docker run -it muneer2ishtech/springboot_multiport_coding_exercise:1.0.0
```

### Run using Docker composer
- This will run PostgreSQL together

```
docker compose -f docker-compose.yml up

```

## Docker Hub
### Push to Docker Hub
- You need to sign in to Docker Hub
- Local maven and docker build should be successful

```
docker compose -f docker-compose.yml push

```

#### To push all tags to Docker Hub
```
docker image push --all-tags muneer2ishtech/springboot_multiport_coding_exercise
```

### Pull from Docker Hub
```
docker pull muneer2ishtech/springboot_multiport_coding_exercise:1.0.0
```

## Run Docker Image pulled from Docker Hub
- To download the executable docker image and run (without any local build)
- Download `public-docker-compose.yml` from [Github](https://github.com/muneer2ishtech/springboot-multiport)
  - This to get docker image from [Docker Hub](https://hub.docker.com/repository/docker/muneer2ishtech/springboot_multiport_coding_exercise)
- To pull the docker image use following command
  - This will get PostgreSQL Docker image also

```
docker compose -f public-docker-compose.yml pull
```

- To Run the docker image, execute following command

```
docker compose -f public-docker-compose.yml up
```

### To Run with additional port for image pulled from Docker Hub
- To use additional ports for public docker image:
  - In `public-docker-compose.yml`, change `FI_ISHTECH_CODINGEXERCISE_MULTIPORT_ADDITIONAL-PORTS` to `true`


## APIs
See [APIs](./README.md#APIs) on how to find APIs to use for application
