# OrderAPI

Covered cases
  CreateOrder, calculate discounts
  H2 DB and initial records provided
  Unit tests for a successfull and a failed case
  Logging with Slf4j
  Program doesn't need any additional configuration to run
  
Calling API

url: http://localhost:8080/api/orders
Method: POST
Example body:
  {
    "items": [
        {
            "drinkId": 1,
            "toppingId": 5
        },
        {
            "drinkId": 1,
            "toppingId": 5
        },
        {
            "drinkId": 1
        }
    ]
}
2 drinks with topping and 1 drink without topping.

Note: Drinks are pre-created. You can find insert commands in resources/data.sql
