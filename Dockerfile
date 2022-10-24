FROM openjdk:11
VOLUME /tmp
EXPOSE 8083
ADD ./target/clients-0.0.1-SNAPSHOT.jar ms-clients.jar
ENTRYPOINT ["java", "-jar", "ms-clients.jar"]