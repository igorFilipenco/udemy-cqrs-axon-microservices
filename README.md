# udemy-cqrs-axon-microservices

This sandbox repository which has been created and developed during this [course](https://www.udemy.com/course/spring-boot-microservices-cqrs-saga-axon-framework/)

## Tech stack:
 - Spring-Boot
 - Lombok
 - Eureka Discovery
 - Spring Gateway
 - Axon
 
## Steps to run the project:
 1. run Axon server(can be found in project folder **axon**) in terminal with command *java -jar axonserver.jar* or you can run Axon server in docker(look for instruction on oficial Axon website)
 2. run **eureka** and **apigateway**  modules
 3. run all other services
 4. import postman collection from **postman** folder and use requests to explore services

