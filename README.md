# Marvel Characters API


## Stack
- Java 18
- Spring Boot
- WebFlux
- Lombok
- Swagger
- Redis

## Docker
- Boot up required containers (redis) `docker compose up -d`

## Commands
- `mvn spring-boot:run` - run server
- `mvn test` - test cases

## TODO
- Config environments
- Translation support
- Ensure swagger output is correct
- Unit tests
- Use github secrets variables for keys
- Javadoc comments
- Compare to open source projects to learn if I can make things more idiomatic
- Fill out this readme
- Tweak `spring.codec.max-in-memory-size` to a bit more than how much 100 rows of character info is
- Some other stuff but I forgot

## Future improvements
- CI/CD