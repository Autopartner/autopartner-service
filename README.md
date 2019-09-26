# Autopartner

## Setup 

### Setup database 

##### Install PostgreSQL

- https://www.postgresql.org/download/ 

##### Install pgAdmin

- https://www.pgadmin.org/download/

##### Execute queries from REST/config/

- initDB.sql
- import.sql

### Setup backend

##### Import gradle project REST

- https://www.jetbrains.com/help/idea/gradle.html

#####  Build REST project 

in the terminal
  ```bash
  $ ./gradlew clean build
  ```
or in the gradle tool window

- clean
- build

##### Run REST project by click RUN on class

```bash
  autopartner.Application
```

### Setup frontend

##### Package installation

```bash
$ cd JS-UI
$ npm install
```

#### Use development server

```bash
$ npm start
```

#### Build assets
To put compiled files into `static` directory, type the following command.

```bash
$ npm run build
```

  

  





