# groli

### Database
For a fast start with the database use following command if you got docker installed:
* &emsp; docker run -d \\  
   &emsp; --name groli-postgres \\  
   &emsp; -p 5432:5432 \\  
   &emsp; -e POSTGRES_DB=groli \\  
   &emsp; -e POSTGRES_USER=postgres \\  
   &emsp; -e POSTGRES_PASSWORD=masterkey \\  
   &emsp; postgres
   
Single-line for windows users:
* docker run -d --name groli-postgres -p 5432:5432 -e POSTGRES_DB=groli -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=masterkey postgres

If you want the container to start every time you boot up, use ```--restart always``` or ```--restart unless-stopped```. Otherwise you have to call ```docker start groli-postgres``` every time you want to use the database after shutting down the system.