FROM openjdk:8u242-jdk-buster
ADD target/springboot-credit-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8050
CMD java -jar springboot-credit-api-0.0.1-SNAPSHOT.jar --HOST_NAME=loanDb --CUSTOMER_API=customer --PRODUCT_API=product