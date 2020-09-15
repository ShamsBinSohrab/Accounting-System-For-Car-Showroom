FROM node:12.18-alpine3.9 as build
RUN mkdir -p /webapp
WORKDIR /webapp
COPY package*.json /webapp
RUN npm install -g @angular/cli @angular-devkit/build-angular && npm install
COPY . /webapp
ARG server_port=4200
EXPOSE $server_port
CMD ["npm", "start"]
