##MUSALA DRONES APPLICATION

:scroll: **START**


### Introduction
This is in fulfilment of the assignment tasks. 
During the course of creating this application 
some assumptions and choices were made. Some of 
those choices/assumptions include the following:
- The application is written in the JAVA programming language and
uses teh SpringBoot framework
- The application uses h2 database as the internal database
- The application implements a basic form of Spring Security 
and authentication happens in-memory
- The username and password is saved in the application.
properties file
- JUnit tests have been implemented, but the coverage is
not as wide as it should be because of time
- Required data has been preloaded in the database
- The scheduler logs the battery level of the drones every 
**1** minute to app.log in the root folder


### How to Build Run and Test the Application
- Open the application on
- This application uses a docker container 
- The Dockerfile and docker-compose.yml file can be found in
the root folder.

####Prerequisite:
- Ensure you have Docker installed on your local machine

####Build:
- Open a terminal and type in **docker-compose build** in
order to build and create a docker image of the application

####Run:
- On the same terminal used to build typically, type in
the following **docker-compose up** to run the application.


####Test:
Open a browser and run the following
- localhost:8082/api/v1/drones




:scroll: **END**