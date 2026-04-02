# ---- Stage 1: Build with Maven ----
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy only pom first (for caching dependencies)
COPY pom.xml .

# Download dependencies (cached if pom doesn't change)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the app ----
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy ONLY the jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the app
CMD ["java", "-jar", "app.jar"]