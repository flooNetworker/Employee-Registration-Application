# Employee Registration Application
This Employee Registration Application is a Spring Boot-based project designed to manage employee data. 
It provides functionalities to register new employees, retrieve employee details, update existing employee information, and delete employees. 
The application uses an H2 in-memory database for data storage and includes unit tests to ensure the correctness of the implemented features. 
The project is built using Maven and follows standard Spring Boot conventions for configuration and dependency management.

![api case etimo (1)](https://github.com/user-attachments/assets/593c4b74-42c1-47af-8504-e433afe9a553)

## How to use the API
The project is a maven project, so you can run it by using the command `mvn spring-boot:run` in the terminal 
OR use an ide like IntelliJ and run the project from there.

*While running the application...*
#### Access the localhost website:
Access the localhost website through http://localhost:8080/api/v1/employees

#### Access the localhost h2 database:
If you want to see the database and perform SELECT queries for example it can be accessed through here:<br>
http://localhost:8080/h2-console<br>
`user: mimmi`<br>
`password: mimmi`

## Try making your own requests
1. Use the build in HTTP client in IntelliJ
Click the globe next to the PUT/POST/GET/DELETE mappings in the controller, click "Generate requests in HTTP client"

2. Use postman or similar

#### Examples:
Link to generated requests [examples](https://github.com/flooNetworker/Employee-Registration-Application/blob/main/src/test/generated-requests.http)
