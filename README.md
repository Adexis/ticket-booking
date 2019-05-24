# Ticket-booking

Basic REST API for ticket booking service Java/Spring boot/Hibernate/Junit


# Running the app

Unfortunately I wasn't able to integrate docker as I wanted to give the result ASAP. The dockerfile is there though, so I can still do it on the weekend If you want me to. For now the project is built by maven only.

First clone the repository

cd into directory of your choice

'mvn -P test-then-run'


## Unit testing

Tests provided with JunitParams for easy parametrization and AssertJ, mainly for the validators.

## Integration testing along with demo

Quite some REST Api tests using postman. In order to run execute following command from project directory

(you need the newman which is the CLI postman so if you dont have it just run

npm install -g newman

to run my tests after running the application:

newman run src/main/resources/ticket-booking.postman_collection.json --delay-request 1000

or you can also import the collection using postman functions and run it from the GUI.

So that's about it I think, the rest is in the code, or I can of course provide more information if you want me to.