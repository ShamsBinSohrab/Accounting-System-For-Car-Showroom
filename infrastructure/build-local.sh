#! /usr/bin/env bash

echo "Building docker images..."

docker build --no-cache -t "migration-service" -f ./backend/migration-service/Dockerfile ./backend/
docker build --no-cache -t "api-service" \
  --build-arg context_path="/" \
  -f ./backend/api-service/Dockerfile ./backend/
docker build --no-cache -t "frontend-webapp" -f ./webapp/Dockerfile ./webapp/

echo "Deploying local docker swarm stack, updating images"
docker stack deploy -c infrastructure/docker-compose.yml ascs
echo "Deployment complete!"