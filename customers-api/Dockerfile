# Etapa 1: Build
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app
# Copiar el wrapper de maven y el pom para descargar dependencias primero (caché)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copiar el código fuente y construir el JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Run
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
# Copiar solo el JAR desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto por defecto (se sobreescribirá por variable de entorno)
EXPOSE 8080 9090

# Punto de entrada con soporte para variables de entorno de Spring
ENTRYPOINT ["java", "-jar", "app.jar"]
