[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b76156f45d66430a9b6d9cfd97026f85)](https://www.codacy.com/app/vlad-kucher/RestaurantVoting?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vlad-kucher/RestaurantVoting&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/vlad-kucher/RestaurantVoting.svg?branch=master)](https://travis-ci.org/vlad-kucher/RestaurantVoting)
[![Dependency Status](https://www.versioneye.com/user/projects/59d978d815f0d7004b3e737c/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/59d978d815f0d7004b3e737c)

Design and implement a REST API using Hibernate/Spring/SpringMVC **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-----------------------------

from the same directory as pom.xml, type:

`mvn jetty:run`

(you must have maven)

-----------------------------

user email: `user@gmail.com`
user password: `password`
user base64: `dXNlckBnbWFpbC5jb206cGFzc3dvcmQ=`

admin email: `admin@gmail.com`
admin password: `admin`
admin base64: `YWRtaW5AZ21haWwuY29tOmFkbWlu`

-----------------------------

### Curl:
> For windows use `Git Bash`


### VoteController (for user):

#### vote (If user votes again the same day after 11:00 he will receive 409 Conflict)
`curl -X POST http://localhost:8080/rest/vote/1 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get current (today's) vote
`curl -s http://localhost:8080/rest/vote -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`


### AdminRestaurantController (for admin):

#### get restaurant by id
`curl -s http://localhost:8080/rest/admin/restaurants/0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get all restaurants
`curl -s http://localhost:8080/rest/admin/restaurants/ -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### create restaurant (name)
`curl -d '{"name":"new restaurant"}' -H "Content-Type: application/json" -X POST http://localhost:8080/rest/admin/restaurants/ -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### update restaurnat
`curl -s -X PUT -d '{"id":2, "name":"New name"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/2 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### delete restaurant
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/2 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get restaurant by name
`curl -s http://localhost:8080/rest/admin/restaurants/by?name=KFC -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`


### DishController (for admin):

#### add dish (name and price in cents) to today's menu for restaurant (id) 
`curl -d '{"name":"new dish", "price":777}' -H "Content-Type: application/json" -X POST http://localhost:8080/rest/admin/dishes?restaurantId=0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get dish by id
`curl -s http://localhost:8080/rest/admin/dishes/0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### delete dish by id
`curl -s -X DELETE http://localhost:8080/rest/admin/dishes/0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`


### UserRestaurantController (for user), returns only restaurants which have a menu for the date:

#### get restaurant with menu and rating for today
`curl -s http://localhost:8080/rest/restaurants/0 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get restaurant with menu and rating for the date
`curl -s http://localhost:8080/rest/restaurants/0/by?date=2017-10-07 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get all restaurants with menu and rating for today
`curl -s http://localhost:8080/rest/restaurants -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get all restaurants with menu and rating for the date
`curl -s http://localhost:8080/rest/restaurants/by?date=2017-09-29 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`


### RegisterController (for unauthorized):

#### register user (name, email and password are necessary)
`curl -d '{"name":"newuser", "email":"new@gmail.com", "password":"123456", "roles" : ["ROLE_USER"]}' -H "Content-Type: application/json" -X POST http://localhost:8080/rest/register`


### ProfileController (for user and admin):

#### get
`curl -s http://localhost:8080/rest/profile -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### delete account
`curl -s -X DELETE http://localhost:8080/rest/profile -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### update profile
`curl -s -X PUT -d '{"id":0, "name":"Updated user", "email":"user@gmail.com", "password":"password", "roles":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/profile -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`
