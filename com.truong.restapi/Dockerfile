FROM openjdk:11
COPY target/com.truong.restapi-1.0.1.RELEASE.jar app
COPY src/main/resources/application.properties app
WORKDIR app
# EXPOSE 8094

ENTRYPOINT ["java", "-jar", "com.truong.restapi-1.0.1.RELEASE.jar"]