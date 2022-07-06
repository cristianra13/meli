FROM adoptopenjdk/openjdk11
ADD ./build/libs/mutant-test-meli-1.0.jar meli-mutants.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/prod/./urandom","-Dspring.profiles.active=prod","-jar","/meli-mutants.jar"]