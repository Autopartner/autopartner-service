FROM openjdk:17
ADD build/libs/autopartner-be-0.0.2-SNAPSHOT.jar autopartner-be-0.0.2-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","autopartner-be-0.0.2-SNAPSHOT.jar"]