## Fetch Backend Internship Challenge

This is RESTful API that allows users to manage points transactions. Users can add points, spend points, and check their current point balances. This project uses Java Springboot so needs to run in an environment that can support that.

## Set up
1. Clone the repository
2. Build the project using Maven, specifically ./mvnw spring-boot:run
3. The application will start in http://localhost:8000

## Usage/Testing
1. Use the curl commands to test. For example: 

curl -X POST http://localhost:8000/api/add \
-H "Content-Type: application/json" \
-d '{"payer": "Justina", "points": 100, "timestamp": "2024-10-17T10:00:00Z"}'

will create and add 100 points to payer (Justina)

2. Spend points will have one key-value pair and none are needed for check balance.
3. API endpoints are: POST /api/add, POST /api/spend, and GET /api/balance: