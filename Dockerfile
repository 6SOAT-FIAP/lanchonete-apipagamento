# Usando a imagem do Maven para compilar o aplicativo
FROM maven:3.8.3-openjdk-17 AS builder

# Instalar o AWS CLI
RUN apt-get update && apt-get install -y awscli

# Configurar o perfil de credenciais
RUN mkdir -p /root/.aws && \
    echo "[default]" > /root/.aws/credentials && \
    echo "aws_access_key_id=your-access-key-id" >> /root/.aws/credentials && \
    echo "aws_secret_access_key=your-secret-access-key" >> /root/.aws/credentials && \
    echo "[default]" > /root/.aws/config && \
    echo "region=us-east-1" >> /root/.aws/config

# Definindo o diretório de trabalho no contêiner
WORKDIR /app

# Copiando o arquivo de definição de projeto e os arquivos de código-fonte
COPY pom.xml .
COPY src ./src

# Compilando o aplicativo e gerando o arquivo JAR
RUN mvn clean install -DskipTests=true

# Usando a imagem do Amazon Corretto para executar o aplicativo
FROM amazoncorretto:17-al2-native-jdk

WORKDIR /app

# Copie o JAR gerado a partir da etapa anterior para o contêiner
COPY --from=builder /app/target/lanchonete-apipagamento-0.0.1.jar .

# Expondo a porta que o aplicativo está ouvindo
EXPOSE 8080

# Comando para iniciar o aplicativo
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "lanchonete-apipagamento-0.0.1.jar"]