# Accounting System for Car Showroom
A management system for an automobile showroom that can perform basic accounting and inventory management. Using this system, users can easily manage vehicles, sell, or add them, store their records, and update or delete them.
#### Docker setup for local environment
---
- From project root `.../Accounting-System-For-Car-Showroom` type this command only once `docker swarm init`.
- Now build docker images by running the shell script, command `./infrastructure/build-local.sh`
- Goto your local host file and add these two lines: 
```
127.0.0.1   api.localhost
127.0.0.1   app.localhost
```
- After the image building is complete you can access `app.localhost` for webapp and `api.localhost` for backend api
- Check all the deployed service, command `docker service ls`
- Scale up a service, command `docker service scale <service_name>=1`
- Scale down a service, command `docker service scale <service_name>=0`
- Remove the entire docker stack of **ascs**, command `docker stack rm ascs`
