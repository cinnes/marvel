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
### Setup Gcloud Project
1. Log in to `console.google.com` via commandline: `gcloud auth login`
2. Create a project: `gcloud projects create marvel-api-1 --name="Marvel API Project"`
3. Set project in `gcloud`: `gcloud config set project marvel-api-1`
4. Find billing account you'd like to use: `gcloud alpha billing accounts list`
5. Link the project to a billing account: `gcloud alpha billing accounts projects link marvel-api-1 --billing-account=<BILLING ACCOUNT ID>`

### Enable Required Services
1. Enable translations: `gcloud services enable translate`
2. Enable secret management: `gcloud services enable secretmanager.googleapis.com`

### Add Marvel API keys to Gcloud Secret Manager
1. Marvel Public Key: `echo -n "<your-public-key>" | gcloud secrets create marvel-api-public-key --data-file=-`
2. Marvel Private Key `echo -n "<your-private-key>" | gcloud secrets create marvel-api-public-key --data-file=-`

### Boot up local redis instance
`docker compose up -d`.

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