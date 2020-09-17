#! /usr/bin/env bash

if [ ! -d "infrastructure" ]
then
    echo "Call the script from the root of the repository, like ./infrastructure/build-local.sh"
    exit 1
fi

export IMAGE_TAG=$(cat /dev/random | date +%s%N)

echo "Building docker images with tag: $IMAGE_TAG..."

docker build -t "migration-service:$IMAGE_TAG" \
  -f ./backend/migration-service/Dockerfile ./backend/

docker build -t "api-service:$IMAGE_TAG" \
  -f ./backend/api-service/Dockerfile ./backend/

docker build -t "webapp:$IMAGE_TAG" \
  -f ./webapp/Dockerfile ./webapp/

echo "Deploying local docker swarm stack, updating images"

docker stack deploy -c infrastructure/docker-compose.yml ascs

echo "Deployment complete!"