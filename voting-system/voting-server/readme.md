-----------------------
HOW TO INSTALL/START :
0)prerequsites 
next software 
- java 1.8
- maven 


1)build project :
Execute from  ... /voting-system/voting-server/ : mvn clean install
-check results 
 go to newly created target directory : /target 
- there should be archive : voting-system-server-1.0.0-SNAPSHOT.zip
Extract the /lib - content of this archive into /lib directory 


2)run : 
From the directory with extracted /lib --> execute:

java -cp "lib/*" org.nho.vs.VotingServerMain 

It should start HTTP-server(SpringBoot+TomCat) on port 8080 AND embedded database 
+ inserts 2 pre-defined users in DataBase :
name: "superman" of type ADMIN
name:  "tester"  of type REGULAR
 

In case of success - there should be message in console as following:

################################### VOTING_SERVER_STARTED ######################################### 

To check that users are created and HTTP-server works fine - check in browser :
http://localhost:8080/persistent-api/user

It should return something like this(HATEOAS-JSON format):

{
    "_links": {
        "self": {
            "href": "http://localhost:8080/persistent-api/user{?page,size,sort}",
            "templated": true
        }
    },
    "_embedded": {
        "user": [
            {
                "type": "ADMIN",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/persistent-api/user/superman"
                    }
                }
            },
            {
                "type": "REGULAR",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/persistent-api/user/tester"
                    }
                }
            }
        ]
    },
    "page": {
        "size": 20,
        "totalElements": 2,
        "totalPages": 1,
        "number": 0
    }
}

----------------
REST-API:
---
A) Add new restaraunt
-
REQUEST: 
PUT : http://localhost:8080/rest-api/rstrn/?rstName=$RESTORAN_NAME?usrName=$ADMIN_USER  
where :
$RESTORAN_NAME - desired name of new Restaraunt 
$ADMIN_USER    - user's unique name(id) with admin's rights 
-
POSSIBLE RESPONSES : 
-In case if restaraunt created  --> HTTP Code = 201 / Created
-In case of missed parameter(s) --> HTTP Code = 400 / Bad Request 
-In case if user doesn't exist OR of invalid type --> HTTP Code = 401 / Unauthorized 
-In case if Restarunt with such name already exists --> HTTP Code = 409 / Conflict
-In case of internal errors during saving in DB --> HTTP Code = 500 / Internal Server Error

---
B) Add Menu to existing restaurant 
-
REQUEST: 
PUT : http://localhost:8080/rest-api/rstrn/menu/
Headers:  Content-Type = application/json 
Body: 
{
  "menu" : {
    "rstId" : $RESTARAUNT_ID,
    "date"  : "$DATE_OF_MENU",
    "user"  : "$ADMIN_USER",
    "dishes" : [ {
      "name"  : "$NameOfDish",
      "price" :  $price   
      }]
  }
}
where :
$RESTORAN_ID - Restaraunt's id  
$DATE        - Date of menu in MM-dd-yyyy format // optional parameter, if not specified - today's date is used   
$ADMIN_USER  - user's unique name(id) with admin's rights 
$NameOfDish  - name of the dish 
$price       - price of the dish 
-
POSSIBLE RESPONSES : 
-In case if menu added --> HTTP Code = 201 / Created
-In case of missed parameter(s) --> HTTP Code = 400 / Bad Request 
-In case if restaraunt not found by given id --> HTTP Code = 404 / Not Found 
-In case if user doesn't exist OR of invalid type --> HTTP Code = 401 / Unauthorized 
-In case if Menu for given restarunt and date was already added--> HTTP Code = 409 / Conflict
-In case of internal errors during saving in DB --> HTTP Code = 500 / Internal Server Error

---
C) Vote for restaraunt 
-
REQUEST: 
POST : http://localhost:8080/rest-api/rstrn/vote?rstId=$RESTORAN_ID&usrName=$USER_ID
where :
$RESTORAN_ID - Restaraunt's id  
$USER_ID     - Id of user of Regular type 
-
POSSIBLE RESPONSES : 
-In case if vote succeds --> HTTP Code = 200 / OK 
-In case of missed parameter(s) --> HTTP Code = 400 / Bad Request 
-In case if restaraunt not found by given id --> HTTP Code = 404 / Not Found 
-In case if user doesn't exist OR of invalid type --> HTTP Code = 401 / Unauthorized 
-In case if adding time if after 11.00 --> HTTP Code = 403 / Forbidden
-In case of internal errors during saving in DB --> HTTP Code = 500 / Internal Server Error


===================
SAMPLES:
-----
1.Add new restaurant 
---
REQUEST: 
PUT : http://localhost:8080/rest-api/rstrn/?rstName=Hudson?usrName=superman  

---
RESPONSE:
HTTP-Status : 201 Created 
Body: Restaraunt added: Restaurant [id=10, name=Hudson]


-----
2.Add menu to created restaraunt
---
REQUEST: 
PUT : http://localhost:8080/rest-api/rstrn/menu/
Headers:  Content-Type = application/json 
Body: 
{
  "menu" : {
    "rstId" : 10,
    "date" : "11-11-2015",
    "user" : "superman",
    "dishes" : [ {
      "name" : "Crab salad",
      "price" : 5.5
    }, {
      "name" : "Tomato soup",
      "price" : 7.25
    }, {
      "name" : "Chili burger",
      "price" : 11.0
    } ]
  }
}
---
RESPONSE:
HTTP-Status : 201 Created 
Body : 
Menu added: MenuForRestaurantRequest [rstId=10, date=2015-11-11, addedBy=superman, 
dishes=[MenuItem [name=Crab salad, price=5.5], 
        MenuItem [name=Tomato soup, price=7.25], 
        MenuItem [name=Chili burger, price=11.0]]]

---
In case if we try menu for the same restaurant and the same date --> 
RESPONSE:
HTTP-Status : 409 Conflict 
Body : Menu already added to Restaurant[id=10] for date: 2015-11-11

-----
3. Vote for restaurant 
---
REQUEST: 
POST : http://localhost:8080/rest-api/rstrn/vote?rstId=10&usrName=tester
---
RESPONSE: 
HTTP-Status : 20o OK 
Body :  Voted OK

---
NEGATIVE case(when voting after 11.00) :
RESPONSE:
HTTP-Status : 403 Forbidden 
Body : Voting is allowed only before 11.00, but now it is: 2015-11-12T13:52:07.974

-----
4. REST API(s) to show the restaurants/menus  

4.1 show all restaurants 
GET http://localhost:8080/persistent-api/restaurants

4.2 show restaurant by id 
GET http://localhost:8080/persistent-api/restaurants/10

4.3 show all menus 
GET http://localhost:8080/persistent-api/menus

4.3 show menus for particular restaurant (by it's id) 
GET http://localhost:8080/persistent-api/restaurants/10/menus





