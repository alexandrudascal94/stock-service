# Watch Store API

## Overview

Create a JVM based backend application using REST. That contains the following
endpoints:
* GET  /api/stocks (get a list of stocks)
* POST /api/stocks (create a stock)
* GET  /api/stocks/1 (get one stock from the list)
* PUT  /api/stocks/1 (update the price of a single stock)

##Improvements
* Add Services and Repository unit tests 

### Technologies used:
* Java 11
* Spring Boot
* Junit 5
* JPA (with H2 database)
* REST-Assured

### API sample


Request:

GET localhost:8080/api/stocks/
        
Response: 
```json
[
    {
        "name": "paper",
        "price": "1.01",
        "lastUpdated": "2021-07-15T11:40:00"
    },
    {
        "name": "pencil",
        "price": "4.01",
        "lastUpdated": "2021-07-23T12:24:00"
    },
    {
        "name": "notebook",
        "price": "6.01",
        "lastUpdated": "2021-07-15T01:54:00"
    },
    {
        "name": "sticker",
        "price": "12.05",
        "lastUpdated": "2021-07-20T04:54:00"
    },
    {
        "name": "glass",
        "price": "1.41",
        "lastUpdated": "2021-07-17T01:54:00"
    }
]
```

Request:

GET localhost:8080/api/stocks/1
        
Response: 
```json
{
  "name": "paper",
  "price": "1.01",
  "lastUpdated": "2021-07-15T11:40:00"
}
```



Request:

POST localhost:8080/api/stocks/
```json
{
  "name": "paper1",
  "price": "1.01",
  "lastUpdated": "2021-07-15T11:40:00"
}
```   
Response:
```
  Location: /api/stocks/6
```



Request:

POST localhost:8080/api/stocks/2
Body
```json

{
  "price": "11.1"
}
```

Response:
```json 
{
  "name": "pencil",
  "price": "11.1",
  "lastUpdated": "2021-07-25T22:13:55.493762"
}
```



### Build and Run

#### Requirements

    Java 11
    Maven 3+

#### Build

clone the git repository or downland the zip file and unzip

To build the application run the command:

```
mvn clean install
```

#### Run
Then run the application with the command:

```
java -jar target/stock-service-0.0.1.jar

```
or 

```
./mvnw spring-boot:run
```

