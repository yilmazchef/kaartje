docker run `
    --name kaartje-db-server `
    --env POSTGRES_USER=intec `
    --env POSTGRES_PASSWORD=intec `
    --env POSTGRES_DB=kaartje `
    --volume ./db:/var/lib/postgresql/data `
    --publish 5432:5432 `
    --rm `
    --detach postgres:latest