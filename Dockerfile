FROM java:8-jdk-alpine
EXPOSE 8080
WORKDIR ./target/
RUN sh -c 'touch ticket-booking-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","ticket-booking-0.0.1-SNAPSHOT.jar"]