# spring-authentication-jwt

A simple authentication system with jwt in Spring Boot!

## Covered functionality:
- Logging in a user which exists in the db
- Role based authorization based on routes
- Refreshing jwt tokens to create a longer session

## How to run
1. Clone the project and open in your favorite IDE
2. Start the Spring Boot project
3. Send requests to the endpoints

## Available Endpoints
- `/api/auth/login`
- `/api/auth/refresh`
- `/api/users/` <- only available to admin users!

You can also take a look at the OpenAPI Documentation to get some reference.

## Example Request
Let us look at an example on how to log in a user. 
<br>
Send a POST Request to `localhost:8080/api/auth/login/`
with the following data:

```json
{
    "email": "jane@example.com",
    "password": "securePW"
}
```
(This user is available in the database)

Which will return the following response:

```json
{
 "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane@example.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAwNjc4MTg3LCJleHAiOjE3MDA3NjQ1ODcsInJvbGUiOiJBRE1JTiJ9.LsythNgqVGdhGyXOJKHgiPn1qb_r_RYi0g4vpTMTboA",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAwNjc4MTg3LCJleHAiOjE3MDEyODI5ODcsInJvbGUiOiJBRE1JTiJ9.K-aNah07o7guk8iN4u7Ufcqf2AEQvknJ4VT1VYxlTSI",
    "role": "ADMIN"
}
```

Made with ♥ by AndyTrendygth
