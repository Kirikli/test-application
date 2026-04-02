## Project start ##
1) Cloning the repository
```bash
git clone https://github.com/Kirikli/test-application.git
```
2) Go to the project folder
```bash
cd test-application
```
3) Build and Start the project
```bash
docker-compose up --build
```

## Now ##

* PostgreSQL will be available on localhost:5432
* Kafka will be available on localhost:9092
* The app is on http://localhost:8080

## API ##
At the start of the project, 3 users are added to the user database with the id
* 11111111-1111-1111-1111-111111111111
* 22222222-2222-2222-2222-222222222222
* 33333333-3333-3333-3333-333333333333


### Create new task ###
Request:
```http
POST http://localhost:8080/api/tasks
```
Body:
```json
{
  "name": "New Task",
  "description": "Task description"
}
```

### Get task by Id ###
Request:
```http
GET http://localhost:8080/api/tasks/{taskId}
```

### Get paginated Tasks ### 
Request:
```http
GET http://localhost:8080/api/tasks?page=1&size=50
```

### Assign of executor ###
Request:
```http
PATCH http://localhost:8080/api/tasks/{taskId}/assign
```
Body:
```json
{
  "name": "New Task",
  "description": "Task description"
}
```

### Changing the Task status ###
Request:
```http
PATCH http://localhost:8080/api/tasks/{taskId}/status
```
Body:
```json
{
    "status" : "IN_PROGRESS"
}
```

## Kafka topics ##
- task-created-event-topic — create task
- assign-executor-event-topic — assign executor
