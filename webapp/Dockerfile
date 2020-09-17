FROM node:12.12 as build
WORKDIR /webapp
COPY package*.json ./webapp/
RUN cd webapp && npm i -g @angular/cli@9.1.12 && npm ci
COPY . .
RUN npm run build-docker

FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /webapp/dist/ascs-webapp /usr/share/nginx/html
