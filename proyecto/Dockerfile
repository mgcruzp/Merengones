From openjdk:17
# FROM tomcat:9.0

# Copia el código fuente de la aplicación a 
COPY . /app

# Establece el directorio de trabajo
WORKDIR /app

# Construye el archivo JAR de la aplicacion
RUN ./mvnw clean install -DskipTests

# Ejecuta la aplicacion Spring Boot cuando se inicia el contenedor
CMD ["java", "-jar", "target/proyecto-0.0.1-SNAPSHOT.jar"]