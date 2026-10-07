FROM eclipse-temurin:21-jre-alpine

# Build and publish this image to the project's container registry, then use it
# as the FROM image for JVM services that need Doppler at runtime.
RUN apk add --no-cache curl ca-certificates \
    && curl -Ls --tlsv1.2 --proto "=https" --retry 3 \
        https://cli.doppler.com/install.sh | sh
