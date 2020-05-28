FROM openjdk:11
WORKDIR /home/www
COPY ./target/eshop.jar ./
CMD ["java", "-Xmx256m", "-jar", "eshop.jar"]
