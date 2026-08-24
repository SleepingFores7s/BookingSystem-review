FROM eclipse-temurin:25-jre
LABEL   authors="SleepingFores7s"
        project="BookingSystem-Review"
        version="1.0.0"

WORKDIR /booking-review

COPY target/*.jar booking-review.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "booking-review.jar"]