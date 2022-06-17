$UserName = Read-Host "Docker Hub Username"
Write-Host "Docker Hub Username: $UserName"
docker build -t $UserName/kaartje:latest .
Write-Host "Docker Image Build Complete"
docker login -u $UserName
Write-Host "Docker Hub Login Complete"
docker push $UserName/kaartje:latest
Write-Host "Docker Image Push Complete"
docker logout
Write-Host "After pushing, you can remove the image from your local machine with the command 'docker rmi $UserName/basket-vision:latest'"