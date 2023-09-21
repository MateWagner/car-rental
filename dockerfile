# Copy Source and Build jar
FROM maven:3.9.2-eclipse-temurin-17-alpine as build
WORKDIR /app
COPY . .
RUN mvn clean
RUN mvn package

# Copy jar to java runtime environment
FROM eclipse-temurin:17-jre-alpine as app
WORKDIR /app
RUN mkdir ./images
VOLUME /app/images
RUN addgroup -S javauser && adduser -S javauser -G javauser && \
    chown -R javauser:javauser ./
USER javauser
COPY --from=build /app/target/*.jar app.jar
CMD "java" "-jar" "app.jar"
