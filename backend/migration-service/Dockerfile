FROM shamsbinsohrab/ascs-base-image as base

WORKDIR /backend
COPY pom.xml .
COPY migration-service migration-service/
RUN mvn -ntp -f migration-service/pom.xml clean package -DskipTests

FROM openjdk:14-alpine
COPY --from=base /backend/migration-service/target/*.jar migration-service.jar
ENTRYPOINT ["java", "-Dhibernate.types.print.banner=false", "-jar", "migration-service.jar"]
