FROM openjdk:22

WORKDIR /app

ADD target/e-commerce-app.jar /app/e-commerce-app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/e-commerce-app.jar"]