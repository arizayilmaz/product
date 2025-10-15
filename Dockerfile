FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY . .
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .env
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
