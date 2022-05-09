# web-projects
My random web projects

## Web Services & API
- bootstrap - REST API that returns a list of dogs from an embedded H2 in memory database
     - a project build with Spring initalizr using dependencies: Web starter, H2, JPA
     - application.properties enable h2 console
     - Dog Controller
     - Location Contriller
     - @RestController, @Service, @Autowired
     - CRUD Repository - LocationRepository.java

- graphql - GraphQL API that returns a list of dogs from an embedded H2 in memory database
     - Create schema with Query and Mutation
     - implement GraphQLQueryResolver and GraphQLMutationResolver
     - Return erros that extend GraphQLError
- eureka-server - Eureka server running on port http://localhost:8761/ with microservice for dogs using Spring Data REST access at localhost:8080/dogs
      - dependencies Eureka Server and Config Client
      - add @EnableEurekaServer annotation
      - application.properties
      - module dogs - microservice for dogs using Spring Data REST
      - dependencies Rest Repositories
      - entity dog, crud repository dogs, data.sql file, application.properties
      - register DogApplication as Eureka client
      - Run: go to http://localhost:8761/ - Eureka service is running
      - go to http://localhost:8762/dogs - Dogs microservice is running



