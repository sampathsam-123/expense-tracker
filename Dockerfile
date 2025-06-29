# ── Dockerfile ──
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw -B clean package -DskipTests
CMD ["java", "-jar", "$(ls target | grep '.jar$')"]