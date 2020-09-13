#! /usr/bin/env bash

echo "Building docker images..."

docker build -t "migration-service" -f ./backend/migration-service/Dockerfile ./backend/
docker build -t "api-service" -f ./backend/api-service/Dockerfile ./backend/

echo "Deploying local docker swarm stack, updating images"
docker stack deploy -c infrastructure/docker-compose.yml ascs
echo "Deployment complete!"