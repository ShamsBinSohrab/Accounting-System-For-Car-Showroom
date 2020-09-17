ARG server_port=8888
FROM shamsbinsohrab/ascs-base-image:2 as base
WORKDIR /backend
COPY pom.xml .
COPY api-service api-service/
RUN mvn -ntp -f api-service/pom.xml clean package -DskipTests
FROM openjdk:14-alpine
ENV SERVER_PORT $server_port
EXPOSE $server_port
COPY --from=base /backend/api-service/target/*.jar api-service.jar
ENTRYPOINT ["java", "-Dhibernate.types.print.banner=false", "-jar", "api-service.jar"]
