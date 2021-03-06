[![CircleCI](https://circleci.com/gh/josDugan/SFG-Pet-Clinic.svg?style=svg)](https://circleci.com/gh/josDugan/SFG-Pet-Clinic)

# MSSC Beer Service
Source code in this repository derives from my studying 
John Thompson's Spring Framework Guru courses.
Learn more about John Thompson and his courses below!
- [Spring Framework Guru](https://springframework.guru/)
- [Spring Boot Microservices with Spring Cloud](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/)

## Default Port Mappings - For Single Host
| Service Name | Port |
| --- | ---|
| Brewery Beer Service | 8080 |
| Brewery Beer Order Service | 8081 |
| Brewery Beer Inventory Service | 8082 |

## Docker commands
Run zipkin for local development:
```
docker run -d -p 9411:9411 openzipkin/zipkin
```

Run default activemq artemis image for local development:
```
docker run -it --rm \
  -p 8161:8161 \
  -p 61616:61616 \
  vromero/activemq-artemis
```
