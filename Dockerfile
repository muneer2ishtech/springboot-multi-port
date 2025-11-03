FROM openjdk:17

VOLUME /tmp

EXPOSE 8080 8081 8082

COPY target/springboot-multiport-0.1.0-SNAPSHOT.jar springboot-multiport.jar

ENTRYPOINT ["java","-jar","/springboot-multiport.jar"]