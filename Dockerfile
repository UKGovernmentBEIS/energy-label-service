FROM eclipse-temurin:17-alpine
COPY ./build/libs/energy-label-service-SNAPSHOT.jar app.jar

RUN apk update && apk upgrade && rm -rf /var/cache/apk/*

ENV TZONE="Europe/London"
RUN apk add --update tzdata \
&& echo "${TZONE}" > /etc/timezone \
&& ln -sf /usr/share/zoneinfo/${TZONE} /etc/localtime

RUN adduser -S elg
USER elg

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar