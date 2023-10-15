# Utilisation d'une image de base contenant Java (Adaptez la version de l'image de base selon vos besoins)
FROM openjdk:8-jre-alpine

# Répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copier le fichier JAR Spring Boot dans le conteneur (Assurez-vous que votre .jar est bien nommé)
COPY target/stats-service.jar app.jar

# Exposer le port sur lequel le microservice écoutera (Adaptez le port selon vos besoins)
EXPOSE 8014

# Commande pour exécuter l'application Spring Boot
CMD ["java", "-jar", "app.jar"]
