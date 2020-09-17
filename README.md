# Accounting System for Car Showroom

A management system for an automobile showroom that can perform basic accounting and inventory management. Using this system, users can easily manage vehicles, sell, or add them, store their records, and update or delete them.

### Docker setup for local environment

- Goto project root directory `.../Accounting-System-For-Car-Showroom/` 
- Initialize docker swarm (only once), command `docker swarm init`
- Build docker images by running the script, command `./infrastructure/build-local.sh`
- Goto your local host file and add these two lines: 
```
127.0.0.1   api.localhost
127.0.0.1   app.localhost
```
- Check all the deployed service, command `docker service ls`
- Access **webapp** from `app.localhost` and **api** from `api.localhost` 
- Scale up a service, command `docker service scale <service_name>=1`
- Scale down a service, command `docker service scale <service_name>=0`
- Remove the entire docker stack of **ascs**, command `docker stack rm ascs`
