# Build steps
FROM public.ecr.aws/docker/library/node:22 as build-frontend
WORKDIR /build
COPY . .
RUN npm install && npx gulp buildAll

FROM public.ecr.aws/docker/library/eclipse-temurin:17 as build-backend
COPY --from=build-frontend /build .
RUN chmod +x gradlew && ./gradlew test bootJar

# Runtime image
FROM public.ecr.aws/docker/library/eclipse-temurin:17-alpine
COPY --from=build-backend ./build/libs/energy-label-service-SNAPSHOT.jar app.jar

RUN apk update && apk upgrade && rm -rf /var/cache/apk/*

ENV TZONE="Europe/London"
RUN apk add --update tzdata \
&& echo "${TZONE}" > /etc/timezone \
&& ln -sf /usr/share/zoneinfo/${TZONE} /etc/localtime

RUN adduser -S elg
USER elg

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar