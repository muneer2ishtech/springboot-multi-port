# ====== Stage 1: Build ======
FROM eclipse-temurin:25-jdk AS build

COPY . .

RUN ./mvnw -B -q clean install -DskipTests=true

# ====== Stage 2: Runtime ======
FROM eclipse-temurin:25-jre

ARG SERVER_PORT=8080
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT=8081
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT=8082
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS=false

EXPOSE ${SERVER_PORT}
EXPOSE ${FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT}
EXPOSE ${FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT}

COPY --from=build target/ishtech-springboot-multiport-*.jar ishtech-springboot-multiport.jar

ENTRYPOINT ["java", "-jar", "ishtech-springboot-multiport.jar"]
