#  significa a imagem base
FROM alpine:latest

# atualiza a imagem e adiciona o arquivo bash
RUN apk update && apk upgrade && apk add bash

# imagem simplificada java
FROM openjdk:8-jdk-alpine

# atualiza os pacotes baixados da imagem pai
RUN apk update && apk upgrade

# copia o pacote da imagem para o diretorio dokerize
RUN mkdir -p /usr/local/dockerize 
ADD  @project.build.finalName@.jar /usr/local/dockerize/

# script para subir o serviço
ADD run.sh run.sh 
RUN chmod +x run.sh 
CMD ./run.sh
