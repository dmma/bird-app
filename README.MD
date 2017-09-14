Client-server application
=========================

Compiling and packaging stage in root folder
> mvn clean package

Server application
__________________

You can launch server application use following command in bird-server directory
> java -jar target/bird-server.jar -port portNumber -proc_count procCount -data dataStorageDirectory

where
   - **-port** - Port number for starting server
   - **-proc_count** - Number of available threads for incoming requests 
   - **-data** - Directory for storing server information

Client application
__________________ 

You can launch server application use following command in bird-client directory
> java -jar target/bird-client.jar -portNumber portNumber -command

where 
   - **-portNumber** - Port number for connection
   - **-command** - Expected one of commands [-remove, -quit, -listbirds, -addbird, -listsightings, -addsighting]