**Description**

Implement a Java web application with RESTful services that will allow the user to register into the user service, which allows users PII fields. Then allow the user to read, update, or delete their own information with proper authentication. Other registered users should not be able to read, update, or delete the details of other users; it should deny access.

Implement a Java web application with RESTful services that allows registered users to execute CRUD operations on the books in a repository. Users should be able to perform "C" (create), "R" (read), "U" (update), "D" (delete) operations with proper authentication.

**Requirements**

1. When run locally, the application’s services should be available at http://localhost:8080/api/v1/books and http://localhost:8080/api/v1/users.
2. *Optional*: If you have time, you can make it as two separate microservices in the same repository which can run on two different ports, 8080 and 8081.
3. Design the DB tables based on the above requirement and include some performance measures.
4. Add security with Spring Security authentication.
5. Add integration test cases which can perform tests from the rest endpoint to the DB level. You can use test containers or any other familiar test dependencies if you wish.
6. Use Docker containers for running the DB and Spring Boot application.
7. User PII information: firstName, lastName, email-Id (username), password.
8. Books information: Title, Author, year, price.

**Project Details**

Code should be based on Maven or Gradle, Java 11 or above, and Spring Boot. Please note that the reviewer should be able to run the solution with a single command without the need to install additional software on their machine.
