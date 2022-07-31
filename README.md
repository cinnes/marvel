# Marvel Characters API


## Stack
- Java 18
- Spring Boot
- WebFlux
- Lombok
- Swagger
- Redis

## Prerequisites
- [`gcloud` commandline tool](https://cloud.google.com/sdk/docs/install)
- JDK 18

## Getting Started
1. Log in to `console.google.com` via commandline: `gcloud auth login`
2. Create a project: `gcloud projects create marvel-api-1 --name="Marvel API Project"`
3. Set project in `gcloud`: `gcloud config set project marvel-api-1`
4. Find billing account you'd like to use: `gcloud alpha billing accounts list`
5. Link the project to a billing account: `gcloud alpha billing accounts projects link marvel-api-1 --billing-account=<BILLING ACCOUNT ID>`
6. Enable translations: `gcloud services enable translate`
7. Enable secret management: `gcloud services enable secretmanager.googleapis.com`
8. Add Marvel Public Key to secretmanager `echo -n "<your-public-key>" | gcloud secrets create marvel-api-public-key --data-file=-`
9. Add Marvel Private Key to secretmanager `echo -n "<your-private-key>" | gcloud secrets create marvel-api-public-key --data-file=-`
10. Boot up redis container: `docker compose up -d` (ensure port 6379 is free)

## Notes
- You can tweak the `app.initialize-data` config to turn off data initialization, by default it will flush all & reload on bootup.
- Docker used to boot a local redis instance to keep setup nice and easy and keep dev data local. In production I'd use memorystore.
- Local development piggybacks off end user credentials when interacting with gcloud.

## Commands
- `./mvnw spring-boot:run` - run server
- `./mvnw test` - test cases

## TODO
- Ensure swagger output is correct
- Research agile, scrum, DORA metrics

## Future improvements
- CI/CD
- Enforced code styling