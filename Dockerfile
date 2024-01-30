FROM amazoncorretto:17.0.10-alpine

ARG version
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JAR_FILE=build/libs/bookwel-api-$version.jar

ENV SPRING_DATASOURCE_URL=${DB_URL}
ENV SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080