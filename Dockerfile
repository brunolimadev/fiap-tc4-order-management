# Usamos a imagem base do OpenJDK para o Java 17
FROM openjdk:17-jdk-alpine

# Definindo a pasta de trabalho dentro do container
WORKDIR /app

# Copiando o arquivo JAR da aplicação para dentro do container
COPY target/order-management-1.0.2.jar /app/order-management-1.0.2.jar

# Expondo a porta em que a aplicação Spring Boot estará rodando
EXPOSE 8082

# Definindo variáveis de ambiente
ENV MONGODB_HOST=
ENV MONGODB_PORT=
ENV MONGODB_DATABASE=
ENV MONGODB_USERNAME=
ENV MONGODB_PASSWORD=
ENV KAFKA_BOOTSTRAP_SERVERS=
ENV KAFKA_TOPIC_ORDER_WAITING_SHIPMENT=
ENV KAFKA_TOPIC_ORDER_SENT=
ENV KAFKA_TOPIC_ORDER_DELIVERED=
ENV PRODUCT_STOCK_URL=

# Comando para rodar a aplicação Java Spring Boot
CMD ["java", "-jar", "order-management-1.0.2.jar"]