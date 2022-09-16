# Autopartner

## Setup

### Setup database

##### Install PostgreSQL

- https://www.postgresql.org/download/

##### Install pgAdmin

- https://www.pgadmin.org/download/

##### Execute queries from /config in pgAdmin

- initDB.sql

##### Build project

in the terminal

  ```bash
  $ ./gradlew clean build
  ```

or in the gradle tool window

- clean
- build

##### Run project

set spring profile to `local`

set evn variables

```dotenv
POSTGRES_PASSWORD=test;
POSTGRES_USERNAME=autopartner_user;
POSTGRES_URL=jdbc:postgresql://localhost:5432/autopartner_db;
AUTH_SECRET=sssshhhh!
```

Run application

```bash
  java -jar build/libs/autopartner-be-0.0.2-SNAPSHOT.jar
```

##### Tests

Run unit tests

```bash
 $ ./gradlew test --info 
```

Run unit tests and integration tests (testcontainers used, docker must be run)

```bash
 $ ./gradlew test -Dtest.profile=integration --info 
```
