FROM openjdk:17-oracle
LABEL authors="Ankit"
EXPOSE 8080
ADD target/be-assignment-zywa.jar be-assignment-zywa.jar
ENTRYPOINT ["java","-jar","/be-assignment-zywa.jar"]