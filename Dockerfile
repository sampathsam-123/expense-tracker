FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw -B clean package -DskipTests
CMD sh -c 'java -jar target/*.jar'