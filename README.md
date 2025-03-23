# Configuration-System

# Server (Configuration Management API)
For the server a Kafka needs to be started, so it can publish the configurations to the appropriate
topics - dev,prod,staging. First the ZooKeeper server needs to be started and after that the Kafka server.

Commands used (should be executed in kafka installation folder):
-       Zookeeper Server: .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
-       Kafka Server: .\bin\windows\kafka-server-start.bat .\config\server.properties

CRUD operations are implemented and when data is changed - it is published to the Kafka topics - dev,prod,staging.
In the resources folder are the configurations for the application (application.properties) and the Database Script,
that includes the schema that needs to be created in order for hibernate to fill up and connect to the database and
create the classes. 

    [Note] After starting the Kafka, you need to start the Server Application and then start and use the Client SDK!

# SDK Client Manual (Configuration System Client SDK)
    Currently there are 3 files added (for simplicity, it can also be implemented as generic approach). 
    Each of the files is a configuration file for different environment - dev,prod,staging. You can find them in the
    sdk client service - i.e. ConfigSystemClientSDK, in the testData folder. You can modify the files and start the 
    application afterwards from the Main class. It makes a Http Request towards the Server which is the main API.

    Usage examples: 
    --- Add new properties into the config files:
    newProperty1=newValue1
    newProperty2=newValue2

    --- Update existing property
    newProperty1=testValue1 
    
    -- Finally just run the Configuration System Client SDK Application
