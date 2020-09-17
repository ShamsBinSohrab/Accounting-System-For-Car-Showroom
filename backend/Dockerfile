FROM maven:3.6-openjdk-14-slim

MAINTAINER aniknishan@gmail.com

WORKDIR /backend
COPY pom.xml .
COPY api-service/ api-service/
COPY migration-service/ migration-service/

RUN mvn -B -U clean install -DskipTests \
  && cd / \
  && rm -rf /backend
WORKDIR /