# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app

COPY pom.xml .
COPY . .

RUN mvn clean package -DskipTests

# Stage 2: Run the app with a smaller JDK image
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/att-tracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
