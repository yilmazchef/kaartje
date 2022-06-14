Write-Host "Kaartje app server is running"    
docker run `
    --name kaartje-db-admin-server `
    --publish 9999:8080 `
    --detach adminer:latest
