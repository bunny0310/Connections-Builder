FROM openjdk

EXPOSE 8080
EXPOSE 8081

COPY UsersService.jar .
COPY config_prod.yml .
CMD ["java","-jar","UsersService.jar","server","config_prod.yml"]