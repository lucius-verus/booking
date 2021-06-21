
This is a standard SpringBoot REST API application that can be run via **java -jar** command or inside an IDE without any special command line arguments.

Application runs on the standard 8080 port with the context path {/booking} -> http://localhost:8080/booking

Storage is done in-memory via an H2 database that can be accessed while the application is running at http://localhost:8080/booking/h2-console ('sa' as username and no password)
or via the following connection string -> 'jdbc:h2:mem:testdb'

A Postman Collection is available inside the main directory to facilitate manual testing

##Enpoints Exposed

######1. Load Photographers:
Given the fact that we use volatile (in-memory) storage, we have to load our Photographers after each application start
- Method: POST
- Endpoint: localhost:8080/booking/photographers
- Request Body:
```json
[
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "phoneNumber": "051237428"
  },
  {
    "firstName": "Mark",
    "lastName": "Newman",
    "email": "mark.newman@email.com",
    "phoneNumber": "043870213"
  },
  {
    "firstName": "Richard",
    "lastName": "Kramer",
    "email": "richard.cramer@email.com",
    "phoneNumber": "075345928"
  }
]
```

######2. Save an Order:
Create a new Order
- Method: POST
- Endpoint: localhost:8080/booking/orders
- Request Body:
```json
{
  "name": "Markus",
  "surName": "Rashford",
  "email": "markus.rashforda@email.com",
  "cellNumber": "01010140",
  "photoType": "Real Estate",

  "title": "Custom Title",
  "logisticInfo": "Large text area",
  "dateTime": "",
  "orderStatus": ""
}
```
######3. Get an Order:
Retrieve an existing Order
- Method: GET
- Endpoint: localhost:8080/booking/orders/{orderId}


######4.a Patch an Order:
Can be used to edit an existing order

- Method: PATCH
- Endpoint: localhost:8080/booking/orders
- Request Body:
```json
{
  "name": "Markus",
  "surName": "Rashford",
  "email": "markus.rashforda@email.com",
  "cellNumber": "01010140",
  "photoType": "Real Estate",

  "title": "Custom Title",
  "logisticInfo": "Large text area",
  "dateTime": "2021-06-21 08:28:58",
  "orderStatus": ""
}
```

######4.b Schedule an Order:
This Endpoint is just a particular use of 4.1 Endpoint
If the Order was initially Created without a DateTime, we use this endpoint to give an Order a DateTime and move into Scheduled status

- Method: PATCH
- Endpoint: localhost:8080/booking/orders
- Request Body:
```json
{
  "dateTime": "2021-06-21 08:28:58"
}
```

######4.c Manually change Order status:
This Endpoint is just a particular use of 4.1 Endpoint
If the Order is initially Created without a DateTime, we use this endpoint to give an Order a DateTime and move into Scheduled status
This Endpoint has strict validation rules:
1. An order can be Cancelled at any time after which it will not longer be open for editing
2. An order with status Uploaded can be move in either Completed status or back to Assigned status
3. All other transitions will be blocked via validation logic

- Method: PATCH
- Endpoint: localhost:8080/booking/orders
- Request Body:
```json
{
  "orderStatus": "Cancelled"
}
```

######5 Assign Photographer to Order
Assign a Photographer to an Order and move the Order in the Assigned status
- Method: PATCH
- Endpoint: localhost:8080/booking/orders/1/photographer/1
- Request Body: none

######6 Upload Photos
Endpoint used by Photographers to upload their photos to Order and change the Order status to Uploaded.
Normally this would be a Multipart Request but in the Requirements docs received it was specified to use a simple String as no files have to actually be uploaded here.

- Method: POST
- Endpoint: localhost:8080/booking/orders/1/photos
- Request Body: **x-www-form-urlencoded**
```text
key: "photos" | value = "Photos"
```

######7 Get Photos
Endpoint used by Operator to check the Photos
He can later use 4.c Endpoint to move the Order into either Completed or Assigned status
- Method: GET
- Endpoint: localhost:8080/booking/orders/1/photos


