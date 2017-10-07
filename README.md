Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

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

user email: ``user@gmail.com``
user password: ``password``
user base64: ``dXNlckBnbWFpbC5jb206cGFzc3dvcmQ=``

admin email: ``admin@gmail.com``
admin password: ``admin``
admin base64: ``YWRtaW5AZ21haWwuY29tOmFkbWlu``

-----------------------------

### Tests 
> For windows use `Git Bash`


### Test VoteController:

#### vote
`curl -X POST http://localhost:8080/rest/vote/1 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get current
`curl -s http://localhost:8080/rest/vote -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`


### Test AdminRestaurantController:

#### get
`curl -s http://localhost:8080/rest/admin/restaurants/0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get all
`curl -s http://localhost:8080/rest/admin/restaurants/ -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### create
`curl -d '{"name":"new restaurant"}' -H "Content-Type: application/json" -X POST http://localhost:8080/rest/admin/restaurants/ -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### update
`curl -s -X PUT -d '{"id":2, "name":"New name"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/2 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### delete
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/2 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get by name
`curl -s http://localhost:8080/rest/admin/restaurants/by?name=KFC -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`


### Test DishController:

#### create
`curl -d '{"name":"new dish", "price":777}' -H "Content-Type: application/json" -X POST http://localhost:8080/rest/admin/restaurants/dishes?restaurantId=0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get
`curl -s http://localhost:8080/rest/admin/restaurants/dishes/0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### delete
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/dishes/0 -H'Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`


### Test UserRestaurantController:

#### get
`curl -s http://localhost:8080/rest/restaurants/0 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get for date
`curl -s http://localhost:8080/rest/restaurants/0/by?date=2017-10-07 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get all
`curl -s http://localhost:8080/rest/restaurants -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get all for date
`curl -s http://localhost:8080/rest/restaurants/by?date=2017-09-29 -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### get one with all menus
`curl -s http://localhost:8080/rest/restaurants/0/all -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`


### Test RegisterController:

#### register user
`curl -d '{"name":"newuser", "email":"new@gmail.com", "password":"123456", "roles" : ["ROLE_USER"]}' -H "Content-Type: application/json" -X POST http://localhost:8080/rest/register`


### Test ProfileController:

#### get
`curl -s http://localhost:8080/rest/user/profile -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### delete
`curl -s -X DELETE http://localhost:8080/rest/user/profile -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

#### update
`curl -s -X PUT -d '{"id":0, "name":"Updated user", "email":"user@gmail.com", "password":"password", "roles":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/user/profile -H'Authorization:Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`
