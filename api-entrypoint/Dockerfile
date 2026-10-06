FROM scobca/java21-doppler:latest

COPY build/libs/api-entrypoint.jar app.jar

ENTRYPOINT ["/bin/sh", "-c", "export DOPPLER_TOKEN=\"$API_ENTRYPOINT_DOPPLER_ACCESS_KEY\"; exec doppler run -- java -jar /app.jar"]
