### Store Microservice
A Microservice made for my software design class project. The project is a webapplication that leads a customer through a store on an optimized path that includes every item they are looking for.

To run the server use the command "mvn liberty:run" in the directory of the cloned repo. By default ports 9081 and 9444 are used for http and https respectively.

### Endpoints:

POST request endpoint to serverip:serverport/store/nav, which takes a list of items, or multiple lists of items (if an employee is shopping multiple lists for different customers), and returns a list of directions, optimized using a modified dijkstra's algorithm, that the employee or customer would follow. When this endpoint is used for the first time it must communicate with the database to pull all the stores information to build a graph of the store that is traversed for path optimization. This means the firs trequest will take slightly longer (around 100ms).
  
POST request endpoint to serverip:serverport/store/update, which takes a list of edited items, a list of their old locations, and a list of new items, and adds them to the database

POST request endpoint to serverip:serverport/store/search, which recieves a string that is matched against item's names in the database and returns matching items that are also in stock
  
GET request endpoint to serverip:serverport/store/items, which returns all item names and their current stock
  

### server.env
To connect to a database:
Add server.env file to /src/main/liberty/config  
Values in server.env:
+ `MYSQL_SERVER=XXXX` - url for MySQL server
+ `MYSQL_PORT=XXXX` - port for MySQL server
+ `MYSQL_DB=XXXX` - MySQL schema/database name to use
+ `MYSQL_USER=XXXX` - MySQL username
+ `MYSQL_PASS=XXXX` - MySQL password

There is also test code that can be used that has stand in data not from a database.
