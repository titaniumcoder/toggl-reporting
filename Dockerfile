#############
### build ###
#############

# base image
FROM gradle:5.6-jdk11 as builder

# set working directory
WORKDIR /app

# add app
COPY . /app

# skip tests until I have a setup with a running Postgresql image as dependency
RUN gradle -Pprod clean bootJar -x test

############
### prod ###
############

# base image (test whether it works with jre too)
FROM azul/zulu-openjdk-alpine:11 as runtime
EXPOSE 5000

RUN apk add fontconfig --no-progress -q

ENV APP_HOME /app

ENV JAVA_OPTS=""

RUN mkdir $APP_HOME

RUN mkdir $APP_HOME/config
RUN mkdir $APP_HOME/log

VOLUME $APP_HOME/log
VOLUME $APP_HOME/config

WORKDIR $APP_HOME

COPY --from=builder /app/server/build/libs/*.jar toggl-reporting.jar

ENTRYPOINT [ "sh", "-c", "echo $JAVA_OPTS; java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar /app/toggl-reporting.jar" ]
CMD ''
