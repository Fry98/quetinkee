FROM openjdk:11
WORKDIR /home/www
COPY ./target/eshop.jar ./
CMD ["java", "-jar", "eshop.jar"]
