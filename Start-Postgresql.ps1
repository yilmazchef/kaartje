docker run `
    --name kaartje-db-server `
    --env POSTGRES_USER=intec `
    --env POSTGRES_PASSWORD=intec `
    --env POSTGRES_DB=kaartje `
    --volume ./db:/var/lib/postgresql/data `
    --rm `
    --detach postgres:latest