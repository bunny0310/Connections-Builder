FROM openjdk

EXPOSE 8080
EXPOSE 8081

COPY UsersService.jar .
COPY config.yml .
CMD ["java","-jar","UsersService.jar","server","config.yml"]