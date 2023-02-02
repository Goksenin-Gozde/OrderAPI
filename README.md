# OrderAPI

## Covered cases <br />
  CreateOrder, calculate discounts <br />
  H2 DB and initial records provided <br />
  Unit tests for a successfull and a failed case <br />
  Logging with Slf4j <br />
  Program doesn't need any additional configuration to run <br />
  
## Calling API <br />
url: http://localhost:8080/api/orders <br />
Method: POST <br />
Example body: <br /> 
```
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
```
2 drinks with topping and 1 drink without topping. <br />

Note: Drinks are pre-created. You can find insert commands in resources/data.sql <br />
