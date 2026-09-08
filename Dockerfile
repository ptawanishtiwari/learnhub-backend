FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the Maven project
COPY . .

# Build the Spring Boot JAR
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR
RUN cp target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]