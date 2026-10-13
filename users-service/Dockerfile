FROM scobca/java21-doppler:latest

COPY build/libs/users-service.jar app.jar

ENTRYPOINT ["/bin/sh", "-c", "export DOPPLER_TOKEN=\"$USERS_SERVICE_DOPPLER_ACCESS_KEY\"; exec doppler run -- java -jar /app.jar"]
