FROM openjdk:17
EXPOSE 8080
COPY target/app-1.0.0.jar rewardsapp.jar
ENTRYPOINT [ "java", "-jar", "rewardsapp.jar"]
