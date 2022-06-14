Write-Host "Kaartje is being built..."
docker build -t yilmazchef/kaartje:latest .
Write-Host "Kaartje app server is running"    
docker run `
    --name kaartje-app-server `
    --publish 8888:8080 `
    --env DB_HOST=localhost `
    --env DB_PORT=5432 `
    --env DB_NAME=kaartje `
    --env DB_USER=intec `
    --env DB_PASSWORD=intec `
    --detach yilmazchef/kaartje:latest
Write-Host "Pushing to Docker Hub"
docker push yilmazchef/kaartje:latest
