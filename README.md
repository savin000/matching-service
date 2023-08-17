# Matching Service

This project is a simple matching service that can find a pair to a user by age and rating.  

## Setup
* Run docker-compose.yml
* Run main class: com.savin.matchingservice.MatchingServiceApplication

## Usage
* Add user entities to request topic: http://localhost:9090/ui/docker-kafka-server/topic/matching-service.match-request  
User entity example: 
```json
{
  "age": 22,  
  "rating": 1500,  
  "internalId": "internal-id-1"  
}
```
* Look for matches here: http://localhost:9090/ui/docker-kafka-server/topic/matching-service.match-result