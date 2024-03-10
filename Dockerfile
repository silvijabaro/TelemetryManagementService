FROM eclipse-temurin:17
# Set the working directory inside the container
WORKDIR /app

COPY build/libs/*.jar app.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your Spring Boot application

CMD ["java", "-jar", "app.jar"]