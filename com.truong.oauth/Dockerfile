FROM openjdk:11
COPY target/com.truong.oauth-1.0.1.RELEASE.jar app
COPY src/main/resources/application.properties app
WORKDIR app
# EXPOSE 8094

ENTRYPOINT ["java", "-jar", "com.truong.oauth-1.0.1.RELEASE.jar"]