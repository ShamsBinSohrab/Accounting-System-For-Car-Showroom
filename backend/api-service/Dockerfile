FROM shamsbinsohrab/ascs-base-image as base

WORKDIR /backend
COPY pom.xml .
COPY api-service api-service/

RUN mvn -ntp -f api-service/pom.xml clean package -DskipTests

FROM openjdk:14-alpine
ARG server_port=8888
ARG context_path="/api-service"
ENV SERVER_PORT $server_port
ENV SERVER_SERVLET_CONTEXT-PATH $context_path
EXPOSE $server_port

COPY --from=base /backend/api-service/target/*.jar api-service.jar
ENTRYPOINT ["java", "-Dhibernate.types.print.banner=false", "-jar", "api-service.jar"]
