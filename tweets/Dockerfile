# define base docker image
FROM openjdk:11
LABEL maintainer="hightarun"
ADD target/tweets-0.0.1-SNAPSHOT.jar tweets-docker.jar
ENTRYPOINT ["java", "-jar", "tweets-docker.jar"]