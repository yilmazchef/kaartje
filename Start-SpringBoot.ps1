docker build -t yilmazchef/kaartje:latest .
docker push yilmazchef/kaartje:latest
docker run `
    --name kaartje-app-server `
    --rm `
    --env DB_HOST=kaartje-db-server `
    --env DB_PORT=8080 `
    --env DB_NAME=kaartje `
    --env DB_USER=intec `
    --env DB_PASSWORD=intec `
    --detach yilmazchef/kaartje:latest