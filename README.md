# Marvel Characters API


## Stack
- Java 18
- Spring Boot
- WebFlux
- Lombok
- Swagger

## Commands
- `mvn spring-boot:run` - run


## Setting up Memory Store
1. Set project in config: `gcloud config set project <project-id>`
2. Enable managed redis: `gcloud services enable redis.googleapis.com`
3. Create memorystore: `gcloud redis instances create marvel-store --size=1 --region=europe-west2`
4. Get host IP of created instance: `gcloud redis instances describe marvel-store --region=europe-west2 | grep host`
5. Create compute engine instance in the same region: `gcloud compute instances create marvel-compute --zone europe-west2-b`
>>>>6. SSH into instance: `gcloud compute ssh marvel-compute --zone europe-west2-b`
7. Install required tools: `sudo apt-get install openjdk-18-jdk-headless maven redis-tools`
8. 

## TODO
- Consider whether h2 in memory db is appropriate for storing pre-loaded data
- Investigate `ts` and whether it should vary or not
- Config environments
- Translation support
- Ensure swagger output is correct
- Unit tests
- Use github secrets variables for keys
- Github actions for CI
- Dockerfile?
- Javadoc comments
- Compare to open source projects to learn if I can make things more idiomatic
- Fill out this readme
- Tweak `spring.codec.max-in-memory-size` to a bit more than how much 100 rows of character info is
- Some other stuff but I forgot