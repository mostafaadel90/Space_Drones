# Space_Drones Project
#### the drone has the potential to leapfrog traditional transportation infrastructure.Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.


## API Specification

### /drones

#### POST
##### Summary:
add new drone

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | CREATED |
| 400 | Bad Request |
| 500 | Internal Server Error |

### /drones

#### GET
##### Summary:

Get all drones. and to load drones that ready for loading use optional boolean query parameter 'isReadyForLoading' 

##### Parameters

| Name | Located in | Description                                   | Required | Schema  |
| ---- |------------|-----------------------------------------------|----------|---------|
| isReadyForLoading | query      | flag to get all drones that ready for loading | No       | boolean |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | SUCCESS |
| 400 | Bad Request |
| 500 | Internal Server Error |

### /drones/{id}/medications

#### POST
##### Summary:

load a drone with list of medications 

##### Parameters

| Name | Located in | Description | Required | Schema  |
| ---- | ---------- |-------------| -------- |---------|
| id | path | drone ID    | Yes | Integer |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | SUCCESS |
| 400 | Bad Request |
| 500 | Internal Server Error |

### /drones/{id}/medications

#### GET
##### Summary:

get list of medications that loaded on a given drone

##### Parameters

| Name | Located in | Description | Required | Schema  |
| ---- | ---------- |-------------| -------- |---------|
| id | path | drone ID    | Yes | Integer |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | SUCCESS |
| 400 | Bad Request |
| 500 | Internal Server Error |


### /drones/{id}/battery-level

#### GET
##### Summary:

return the battery level for a given drone

##### Parameters

| Name | Located in | Description | Required | Schema  |
| ---- | ---------- |-------------| -------- |---------|
| id | path | drone ID    | Yes | Integer |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | SUCCESS |
| 400 | Bad Request |
| 500 | Internal Server Error |

### requirement to set up the service

- Java 17
- maven
- postman to import Drones.postman_collection.json as postman collection
  to test the application

### Step 1  *build the service using maven (packaging)*
```bash
    mvn clean package
```
### Step 2 - *run the service locally *
```bash
    java -jar ./target/Drones-1.0.0-SNAPSHOT.jar
```
### Step 4  *test the service*
- import Drones.postman_collection.json as postman collection
- change the baseUrl var on postman with your server url and running port number example
  http://localhost:8080
- test all endpoints

### _Technical Hints_
- At the start of the service there are 5 drones preloaded into the DB in IDLE state
- There is a scheduler that tic every 20 second to check the drones battery level and decrease it by 1% every time.
- Serial Numbers of drones is automatic randomly generated and no need to generate or use it

