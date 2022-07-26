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
- Research agile, scrum, DORA metrics

## Considerations
- Constructor injection only using `@RequiredArgsConstructor` and setting dependencies as `private final`. Immutability = good.
- `CharacterRepository` implemented to interact directly with redis in a service like layer. Would use something like `@Cacheable` if there were a primary implementation.
- 

## Future improvements
- CI/CD
- Enforced code styling