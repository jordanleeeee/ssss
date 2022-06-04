FROM        eclipse-temurin:17.0.3_7-jdk
LABEL       app=ssss
RUN         addgroup --system app && adduser --system --no-create-home --ingroup app app
USER        app
COPY        ./build/libs/ssss-0.0.1-SNAPSHOT.jar        /opt/java

CMD ["java", "-jar", "/opt/java/ssss-0.0.1-SNAPSHOT.jar"]
