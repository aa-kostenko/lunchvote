Top java Graduation Project
===============================
REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Voting system for deciding where to have lunch.

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users

Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)

Menu changes each day (admins do the updates)

Users can vote on which restaurant they want to have lunch at

Only one vote counted per user

If user votes again the same day:

If it is before 11:00 we assume that he changed his mind.

If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

===========================

Working environment to run:

java version 15

apache-maven (i am using 3.6.1)

===========================

To fast run you may use configured maven-cargo plugin:

goal cargo:run

===========================

Curl commands (export from Insomnia) as example of using API.

===========================

***
RESTAURANTS (/rest/restaurants)
***
GET ALL

curl --request GET \
--url http://localhost:8080/lunchvote/rest/restaurants \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

GET 1

curl --request GET \
--url http://localhost:8080/lunchvote/rest/restaurants/1 \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

GET ALL HAS MENU TODAY

curl --request GET \
--url http://localhost:8080/lunchvote/rest/restaurants/hasMenuToday \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

CREATE WITH LOCATION

curl --request POST \
--url http://localhost:8080/lunchvote/rest/restaurants \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE=' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"name":"Новый ресторан"}'

UPDATE 1

curl --request PUT \
--url http://localhost:8080/lunchvote/rest/restaurants/1 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE=' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"id": 1, "name":"Бумбараш (измененный)"}'

DELETE 1

curl --request DELETE \
--url http://localhost:8080/lunchvote/rest/restaurants/1 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

***
LUNCHMENUITEMS (/rest/lunchmenuitems)
***

GET ALL TODAY FOR RESTAURANT 4

curl --request GET \
--url 'http://localhost:8080/lunchvote/rest/lunchmenuitems/allToday?restaurantId=4' \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

GET 1

curl --request GET \
--url http://localhost:8080/lunchvote/rest/lunchmenuitems/1 \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

CREATE WITH LOCATION

curl --request POST \
--url http://localhost:8080/lunchvote/rest/lunchmenuitems/ \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE=' \
--header 'Content-Type: application/json' \
--data '{ "restaurantId": 4, "name": "Новое блюдо", "menuDate": "2021-05-29" , "price": "222.54"}'

UPDATE MENU ITEM 16

curl --request PUT \
--url http://localhost:8080/lunchvote/rest/lunchmenuitems/16 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE=' \
--header 'Content-Type: application/json' \
--data '{ "restaurantId": 4, "name": "Новое блюдо (стало еще новее)", "menuDate": "2021-05-29" , "price": "222.54"}'

DELETE 1

curl --request DELETE \
--url http://localhost:8080/lunchvote/rest/lunchmenuitems/1 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

***
VOTES (/rest/votes)
***

GET ALL (FOR ALL TIME, ONLY FOR ADMINS)

curl --request GET \
--url http://localhost:8080/lunchvote/rest/votes/all \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

GET ALL FOR USER 1 (FOR ALL TIME)

curl --request GET \
--url http://localhost:8080/lunchvote/rest/votes \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

GET 4 FOR USER 1

curl --request GET \
--url http://localhost:8080/lunchvote/rest/votes/4 \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ=='

GET ALL TODAY FOR USER 3

curl --request GET \
--url http://localhost:8080/lunchvote/rest/votes/today \
--header 'Authorization: Basic dXNlcjNAeWFuZGV4LnJ1OnBhc3N3b3JkMw=='

GET VOTE RESULT TODAY (RESTAURANT - VOTES COUNT)

curl --request GET \
--url http://localhost:8080/lunchvote/rest/votes/todayResult \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

VOTE USER 1 FOR RESTAURANT 1

curl --request POST \
--url http://localhost:8080/lunchvote/rest/votes \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ==' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"restaurantId":"1"}'

VOTE USER 2 FOR RESTAURANT 4

curl --request POST \
--url http://localhost:8080/lunchvote/rest/votes \
--header 'Authorization: Basic dXNlcjJAeWFuZGV4LnJ1OnBhc3N3b3JkMg==' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"restaurantId":"4"}'

UPDATE USER 1 VOTE WITH ID 8 TO RESTAURANT 4

curl --request PUT \
--url http://localhost:8080/lunchvote/rest/votes/8 \
--header 'Authorization: Basic dXNlcjFAeWFuZGV4LnJ1OnBhc3N3b3JkMQ==' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"restaurantId":"4"}'

DELETE VOTE 7 (ONLY FOR ADMINS)

curl --request DELETE \
--url http://localhost:8080/lunchvote/rest/votes/7 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

***
USERS/PROFILE (/rest/profile)
***

REGISTER (AS USER)

curl --request POST \
--url http://localhost:8080/lunchvote/rest/profile/register \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"name":"TestUser","email":"test@mail.ru","password":"test-pass"}'

GET PROFILE (FOR test@mail.ru)

curl --request GET \
--url http://localhost:8080/lunchvote/rest/profile \
--header 'Authorization: Basic dGVzdEBtYWlsLnJ1OnRlc3QtcGFzcw=='

UPDATE PROFILE (FOR test@mail.ru)

curl --request PUT \
--url http://localhost:8080/lunchvote/rest/profile \
--header 'Authorization: Basic dGVzdEBtYWlsLnJ1OnRlc3QtcGFzcw==' \
--header 'Content-Type: application/json' \
--data '{"name":"TestUserNEW","email":"test@mail.ru","password":"test-pass"}'

DELETE PROFILE (FOR test@mail.ru)

curl --request DELETE \
--url http://localhost:8080/lunchvote/rest/profile/ \
--header 'Authorization: Basic dGVzdEBtYWlsLnJ1OnRlc3QtcGFzcw=='

***
USERS/ADMIN (/rest/admin/users)
***

GET ALL USERS

curl --request GET \
--url http://localhost:8080/lunchvote/rest/admin/users \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

GET USER BY ID 3

curl --request GET \
--url http://localhost:8080/lunchvote/rest/admin/users/3 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

GET USER BY  EMAIL USER2@YANDEX.RU

curl --request GET \
--url 'http://localhost:8080/lunchvote/rest/admin/users/by?email=user2%40yandex.ru' \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='

CREATE USER BY ADMIN

curl --request POST \
--url http://localhost:8080/lunchvote/rest/admin/users \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE=' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"name":"New User1","email":"test1@mail.ru","password":"test-password1"}'

UPDATE USER BY ADMIN

curl --request PUT \
--url http://localhost:8080/lunchvote/rest/admin/users/6 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE=' \
--header 'Content-Type: application/json;charset=UTF-8' \
--data '{"id":6,"name":"User1","email":"fakeadmin@gmail.com","password":"password","enabled":true,"registered":"2021-05-24T18:59:31.833+00:00","roles":["USER"]}'

DELETE USER BY ADMIN

curl --request DELETE \
--url http://localhost:8080/lunchvote/rest/admin/users/6 \
--header 'Authorization: Basic YWRtaW4xQGdtYWlsLmNvbTphZG1pbjE='