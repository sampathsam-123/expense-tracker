FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw -B clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/expense-tracker-0.0.1-SNAPSHOT.jar"]