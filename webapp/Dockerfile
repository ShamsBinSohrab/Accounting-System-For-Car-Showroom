FROM node:12.12-alpine as build
WORKDIR /webapp
COPY package.json ./webapp/
RUN cd webapp && npm install
COPY . .
RUN npm run build-docker

FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /webapp/dist/ascs-webapp /usr/share/nginx/html
