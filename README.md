# java-spring-cloud-ms

# Java Microservices with Spring Boot and Spring Cloud

# Phase 1: Simple communication, Feign, Ribbon

### 1.1 Create hr-worker project

### 1.2 Implement hr-worker project

Script SQL
```sql
INSERT INTO tb_worker (name, daily_Income) VALUES ('Bob', 200.0);
INSERT INTO tb_worker (name, daily_Income) VALUES ('Maria', 300.0);
INSERT INTO tb_worker (name, daily_Income) VALUES ('Alex', 250.0);
```

application.properties
```
spring.application.name=hr-worker
server.port=8001

# Database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 1.3 Create hr-payroll project

application.properties
```
spring.application.name=hr-payroll
server.port=8101
```

### 1.4 Implement hr-payroll project (mock)

### 1.5 RestTemplate

### 1.6 Feign

### 1.7 Ribbon load balancing

Run configuration
```
-Dserver.port=8002
```
# Phase 2: Eureka, Hystrix, Zuul

### 2.1 Create hr-eureka-server project

### 2.2 Configure hr-eureka-server

Default port: 8761

Access the dashboard in the browser: http://localhost:8761

### 2.3 Configure Eureka clients

Eliminate the hr-payroll Ribbon:
- Maven dependence
- Annotation in the main program
- Configuration in application.properties

Attention: wait a while after going up the microservices

### 2.4 Random port for hr-worker

```
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
```

Warning: delete multiple hr-worker execution settings

### 2.5 Fault tolerance with Hystrix

### 2.6 Timeout of Hystrix e Ribbon

Attention: test before without Hystrix annotation

```
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
ribbon.ConnectTimeout=10000
ribbon.ReadTimeout=20000
```

### 2.7 Create hr-zuul-server project

### 2.8 Configure hr-zuul-server

Default port: 8765

### 2.9 Random port for hr-payroll


### 2.10 Zuul timeout

Even the Hystrix and Ribbon timeout configured in a microservice, if Zuul does not have its timeout configured, for him it will be a timeout problem. So we need to configure the timeout in Zuul.

If the timeout is configured only in Zuul, Hystrix will call the alternative method on the specific microservice.

# Phase 3: Centralized configuration

### 3.1 Create hr-config-server project

### 3.2 Configure hr-config-server project

When a microservice is started up, before registering with Eureka, it fetches the settings from the central settings repository.

hr-worker.properties
```
test.config=My config value default profile
```
hr-worker-test.properties
```
test.config=My config value test profile
```
Teste:
```
http://localhost:8888/hr-worker/default
http://localhost:8888/hr-worker/test
```

### 3.3 hr-worker as a configuration server client, active profiles

In the bootstrap.properties file we configure only what is related to the configuration server, and also the project profile.

Attention: the bootstrap.properties settings have priority over those of the application.properties

### 3.4 Actuator to update settings at runtime

Attention: put @RefreshScope in every class that has some access to the settings

### 3.5 Private Git Repository

Attention: restart the IDE after adding the environment variables

# Phase 4: authentication and authorization

### 4.1 Create hr-user project

### 4.2 Configure hr-user project

### 4.3 User, Role and N-N association entities

### 4.4 Initial database load
```sql
INSERT INTO tb_user (name, email, password) VALUES ('Nina Brown', 'nina@gmail.com', '$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu');
INSERT INTO tb_user (name, email, password) VALUES ('Leia Red', 'leia@gmail.com', '$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu');

INSERT INTO tb_role (role_name) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (role_name) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
```

### 4.5 UserRepository, UserResource, Zuul config

### 4.6 Create hr-oauth project

### 4.7 Configure hr-oauth project

### 4.8 UserFeignClient

### 4.9 JWT Token login and generation

Source -> Override -> configure(AuthenticationManagerBuilder)

Source -> Override -> authenticationManager()

Basic authorization = "Basic " + Base64.encode(client-id + ":" + client-secret)

### 4.10 Authorization of resources through the Zuul gateway

### 4.11 Leaving the Postman top

Variables:
- api-gateway: http://localhost:8765
- config-host: http://localhost:8888
- client-name: CLIENT-NAME
- client-secret: CLIENT-SECRET
- username: leia@gmail.com
- password: 123456
- token: 

Script to assign token to the Postman environment variable:
```js
if (responseCode.code >= 200 && responseCode.code < 300) {
    var json = JSON.parse(responseBody);
    postman.setEnvironmentVariable('token', json.access_token);
}
```
### 4.12 Security configuration for the configuration server

### 4.13 Configuring CORS

Test in the browser:
```js
fetch("http://localhost:8765/hr-worker/workers", {
  "headers": {
    "accept": "*/*",
    "accept-language": "en-US,en;q=0.9,pt-BR;q=0.8,pt;q=0.7",
    "sec-fetch-dest": "empty",
    "sec-fetch-mode": "cors",
    "sec-fetch-site": "cross-site"
  },
  "referrer": "http://localhost:3000",
  "referrerPolicy": "no-referrer-when-downgrade",
  "body": null,
  "method": "GET",
  "mode": "cors",
  "credentials": "omit"
});
```

---------------------
# Creating and testing Docker containers

## Create docker network for hr system
```
docker network create hr-net
```

## Testing dev profile with Postgresql in Docker
```
docker pull postgres: 12-alpine

docker run -p 5432: 5432 --name hr-worker-pg12 --network hr-net -e POSTGRES_PASSWORD = 1234567 -e POSTGRES_DB = db_hr_worker postgres: 12-alpine

docker run -p 5432: 5432 --name hr-user-pg12 --network hr-net -e POSTGRES_PASSWORD = 1234567 -e POSTGRES_DB = db_hr_user postgres: 12-alpine
```

## hr-config-server
```
FROM openjdk: 11
VOLUME / tmp
EXPOSE 8888
ADD ./target/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server.jar
ENTRYPOINT ["java", "- jar", "/ hr-config-server.jar"]
```
```
mvnw clean package

docker build -t hr-config-server: v1.

docker run -p 8888: 8888 --name hr-config-server --network hr-net -e GITHUB_USER = wave -e GITHUB_PASS = hr-config-server: v1
```

## hr-eureka-server
```
FROM openjdk: 11
VOLUME / tmp
EXPOSE 8761
ADD ./target/hr-eureka-server-0.0.1-SNAPSHOT.jar hr-eureka-server.jar
ENTRYPOINT ["java", "- jar", "/ hr-eureka-server.jar"]
```
```
mvnw clean package

docker build -t hr-eureka-server: v1.

docker run -p 8761: 8761 --name hr-eureka-server --network hr-net hr-eureka-server: v1
```

## hr-worker
```
FROM openjdk: 11
VOLUME / tmp
ADD ./target/hr-worker-0.0.1-SNAPSHOT.jar hr-worker.jar
ENTRYPOINT ["java", "- jar", "/ hr-worker.jar"]
```
```
mvnw clean package -DskipTests

docker build -t hr-worker: v1.

docker run -P --network hr-net hr-worker: v1
```

## hr-user
```
FROM openjdk: 11
VOLUME / tmp
ADD ./target/hr-user-0.0.1-SNAPSHOT.jar hr-user.jar
ENTRYPOINT ["java", "- jar", "/ hr-user.jar"]
```
```
mvnw clean package -DskipTests

docker build -t hr-user: v1.

docker run -P --network hr-net hr-user: v1
```

## hr-payroll
```
FROM openjdk: 11
VOLUME / tmp
ADD ./target/hr-payroll-0.0.1-SNAPSHOT.jar hr-payroll.jar
ENTRYPOINT ["java", "- jar", "/ hr-payroll.jar"]
```
```
mvnw clean package -DskipTests

docker build -t hr-payroll: v1.

docker run -P --network hr-net hr-payroll: v1
```

## hr-oauth
```
FROM openjdk: 11
VOLUME / tmp
ADD ./target/hr-oauth-0.0.1-SNAPSHOT.jar hr-oauth.jar
ENTRYPOINT ["java", "- jar", "/ hr-oauth.jar"]
```
```
mvnw clean package -DskipTests

docker build -t hr-oauth: v1.

docker run -P --network hr-net hr-oauth: v1
```

## hr-api-gateway-zuul
```
FROM openjdk: 11
VOLUME / tmp
EXPOSE 8765
ADD ./target/hr-api-gateway-zuul-0.0.1-SNAPSHOT.jar hr-api-gateway-zuul.jar
ENTRYPOINT ["java", "- jar", "/ hr-api-gateway-zuul.jar"]
```
```
mvnw clean package -DskipTests

docker build -t hr-api-gateway-zuul: v1.

docker run -p 8765: 8765 --name hr-api-gateway-zuul --network hr-net hr-api-gateway-zuul: v1
```

## Some Docker commands
Create a Docker network
```
docker network create <network-name>
```
Download Dockerhub image
```
docker pull <image-name: tag>
```
View images
```
docker images
```
Create / run an image container
```
docker run -p <export-external>: <international-port> --name <container-name> --network <network-name> <image-name: tag>
```
List containers
```
ps docker

ps -a docker
```
Track running container logs
```
docker logs -f <container-id>
```
