FROM openjdk:8-jdk-alpine
MAINTAINER musalasoft.com
EXPOSE 8082
COPY target/chukwuemeka-vin-anuonye-1.0.0.jar chukwuemeka-vin-anuonye-1.0.0.jar
ENTRYPOINT ["java","-jar","/chukwuemeka-vin-anuonye-1.0.0.jar"]
