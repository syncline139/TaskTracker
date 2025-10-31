# build-этап
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /src
COPY . .
RUN ./gradlew --no-daemon clean bootJar
RUN JAR=$(ls build/libs/*.jar | grep -v 'plain' | head -n1) && mv "$JAR" /app.jar

# run-этап
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
