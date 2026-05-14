# ====== Stage 1: Build ======
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

COPY . .

ARG MAVEN_CLI_OPTS="-B -q -s .mvn/settings.xml"

RUN chmod +x ./mvnw
RUN echo $(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) > .projectVersion

RUN ./mvnw $MAVEN_CLI_OPTS clean package -DskipTests=true

# ====== Stage 2: Runtime ======
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/.projectVersion .projectVersion

RUN export APP_VERSION=$(cat .projectVersion)

ARG SERVER_PORT=8080
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT=8081
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT=8082
ARG FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_ADDITIONAL_PORTS=false

EXPOSE ${SERVER_PORT:-8080}
EXPOSE ${FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_BOOK_PORT}
EXPOSE ${FI_ISHTECH_PRACTICE_SPRINGBOOT_MULTIPORT_USER_PORT}

COPY --from=build /app/target/ishtech-springboot-multi-port-${APP_VERSION-*}.jar ishtech-springboot-multi-port.jar

ENTRYPOINT ["java", "-jar", "ishtech-springboot-multi-port.jar"]
