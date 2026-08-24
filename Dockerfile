FROM eclipse-temurin:21-jre

LABEL version="1.0.0"

WORKDIR /booking-review

COPY target/*.jar booking-review.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "booking-review.jar"]