FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /booking-review

COPY --from=builder /app/target/*.jar /booking-review/app.jar

EXPOSE 8083

CMD ["java", "-jar", "app.jar"]