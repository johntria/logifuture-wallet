FROM openjdk:21-oracle AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

FROM builder AS tester
RUN ./mvnw test

FROM openjdk:21-oracle AS runner
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]