FROM eclipse-temurin:17-alpine

LABEL authors="zepolar"

RUN addgroup -S zepolar && adduser -S zepolar -G zepolar

RUN mkdir /app && chown zepolar:zepolar /app

WORKDIR /app

ENV DB_USER="" \
    DB_NAME="" \
    DB_HOST="localhost" \
    DB_PORT="5432" \
    DB_PASSWORD="" \
    TZ="Europe/Rome" \
    LANG="en_US.UTF-8" \
    LANGUAGE="en_US:en"

COPY --chown=zepolar:zepolar ./target/demo-zepolar.jar /app

USER zepolar

ENTRYPOINT ["java", "-jar", "demo-zepolar.jar"]