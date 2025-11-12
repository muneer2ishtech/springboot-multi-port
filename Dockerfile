# ====== Stage 1: Build ======
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

COPY . .

ARG MAVEN_CLI_OPTS="-B -q -s .mvn/settings.xml"

RUN chmod +x ./mvnw

RUN ./mvnw $MAVEN_CLI_OPTS clean install -DskipTests=true

# ====== Stage 2: Runtime ======
FROM eclipse-temurin:25-jre

WORKDIR /app

ARG SERVER_PORT=8080
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT=8081
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT=8082
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS=false

EXPOSE ${SERVER_PORT}
EXPOSE ${FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT}
EXPOSE ${FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT}

COPY --from=build /app/target/ishtech-springboot-multi-port-*.jar ishtech-springboot-multi-port.jar

ENTRYPOINT ["java", "-jar", "ishtech-springboot-multi-port.jar"]
