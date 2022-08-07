FROM openjdk:11
COPY src/main/resources/schedulePdf src/main/resources/schedulePdf
ADD target/loans-0.0.1-SNAPSHOT.jar loans-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/loans-0.0.1-SNAPSHOT.jar"]