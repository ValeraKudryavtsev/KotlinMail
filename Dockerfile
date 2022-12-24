FROM openjdk:17
COPY build/libs/KotlinMail-1.0.0.jar KotlinMail-1.0.0.jar
ENTRYPOINT ["java", "-jar", "KotlinMail-1.0.0.jar"]