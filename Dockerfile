FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dspring.profiles.active=dev

FROM openjdk:11
COPY --from=build /home/app/target/template.jar /usr/local/lib/template.jar

EXPOSE 8090
ENTRYPOINT [ "java", "-Dspring.profiles.active=dev", "-jar", "/usr/local/lib/template.jar" ]
