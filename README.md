# hotel-review-board
Platform for hotel reviews assessed with machine learning

# Requirements
 - docker
 - docker-compose

# Deployment
```bash
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --build -d
```

# Configuration
docker-compose.yml
	- MySQL:
		user = root
		password = alabala
		database = hotel-review
		port = 3306
	- Web App:
		port = 8080
		internal -> ./platform/src/main/resources/application-docker.properties
	- ML review prediction App:
		data dir = ./ml/resources
		model data = model.pickle
		vectorizer data = vectorizer.pickle
	- Kafka:
		port = 9092
