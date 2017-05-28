## A REST API for work time tracking

This API handles projects, tasks, and work time intervals.

Projects have title, description and hourly wage.
Tasks have title and a bonus. Work intervals have a beginning and ending timestamp.

The API can calculate the amount of income for projects and tasks, and the time spent working on them in seconds.

The API uses an in-memory HSQL database by default, but an MySQL driver is also provided in the classpath.
The configuration of the database is in the database.properties file.
The configuration of Hibernate is in hibernate.properties.

A client for the application: https://github.com/ViktorSimko/WTimeClient

The routes of the application are the following:

- GET /users/register/:username/:password
- POST /oauth/token
- POST /projects
- GET /projects
- GET /projects/:projectId
- PATCH /projects/:projectId
- DELETE /projects/:projectId
- GET /projects/:projectId/allIncome
- GET /projects/:projectId/allWorkedTime
- POST /tasks
- GET /tasks
- GET /tasks/:taskId
- PATCH /tasks/:taskId
- DELETE /tasks/:taskId
- GET /tasks/:taskId/allIncome
- GET /tasks/:taskId/allWorkedTime
- POST /work_intervals
- GET /work_intervals
- GET /work_intervals/:workIntervalId
- PATCH /work_intervals/:workIntervalId
- DELETE /work_intervals/:workIntervalId

All the routes are private and secured by Spring Security except from the first two.
To access the resources:
1. Send a GET to /users/register/:username/:password

2. Send a POST to /oauth/token?grant_type=password&username=:username&password=:password
   Use HTTP Basic auth with 'wtime-client' as username and 'secret' as password.

3. In the response there is an access_token. Add it to the end of the private routes as a query parameter: 
   POST /tasks?access_token=:access_token


