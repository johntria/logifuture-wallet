## How to Run It
To run the application, you'll need [Docker](https://docs.docker.com/engine/install/). Both the database and backend are dockerized to simplify installations. You can use the following command:

```bash
docker compose up --build
```

## How to Test the REST APIs
You can import the [collection](https://github.com/johntria/logifuture-wallet/blob/main/postman-collection.json) into [Postman](https://www.postman.com/downloads/). It includes most of the use cases.

## What's Been Done
All the specified requirements from the [PDF](https://github.com/johntria/logifuture-wallet/blob/main/assigment.pdf) have been implemented.

## What Wasn't Completed Due to Time Constraints
- Achieving good test coverage for unit or integration testing.
- Setting up staging in Docker to run tests first, followed by local deployment.
- Minimizing the size of Docker images.
- Removing user identifiers from DTOs and externalizing them (e.g., using Spring Security).

## How to Run Tests
To run tests, you'll need to install **Java (version 21) and Docker on your local machine**. Then, you can execute the following command:

```bash
./mvnw test
```

This uses the embedded Maven. Note that in the tests, I've used TestContainer, which cannot be utilized within a Docker image due to the need for Docker in Docker (DinD).
