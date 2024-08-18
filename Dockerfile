FROM openjdk:17
VOLUME /tmp
COPY target/springboot-ollama-Handy.jar springboot-ollama-Handy.jar
ENTRYPOINT ["java","-jar","/springboot-ollama-Handy.jar","--spring.profiles.active=prod"]