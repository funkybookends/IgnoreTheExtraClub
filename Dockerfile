FROM ewolff/docker-java
LABEL version=0.0.1
COPY build/libs/IgnoreTheExtraClub-0.0.1-SNAPSHOT.jar .
CMD /usr/bin/java -jar IgnoreTheExtraClub-0.0.1-SNAPSHOT.jar
EXPOSE 8080