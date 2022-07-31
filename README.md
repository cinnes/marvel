# Marvel Characters API


## Stack
- Java 18
- Spring Boot
- WebFlux
- Lombok
- Swagger
- Redis

## Prerequisites
- `gcloud` commandline tool
- JDK 18

## Getting Started
1. Log in to `console.google.com` via commandline: `gcloud auth login`
2. Create a project: `gcloud projects create marvel-api-1 --name="Marvel API Project"`
3. Set project in `gcloud`: `gcloud config set project marvel-api-1`
4. Find billing account you'd like to use: `gcloud alpha billing accounts list`
5. Link the project to a billing account: `gcloud alpha billing accounts projects link marvel-api-1 --billing-account=<BILLING ACCOUNT ID>`
6. Enable translations: `gcloud services enable translate`
7. Boot up redis container: `docker compose up -d`

## Commands
- `./mvnw spring-boot:run` - run server
- `./mvnw test` - test cases

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