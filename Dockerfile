FROM openjdk:22

ARG jarFile=target/SpringLibraryProject-0.0.1-SNAPSHOT.war

WORKDIR /opt/app

COPY ${jarFile} library.war

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "library.war"]