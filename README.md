# Israf app

My project is a web application that allows restaurants to distribute food, and users can use the application to see where the restaurants are located on the map and come to pick up the food.

Restaurants have their own accounts, which have the function of opening and closing the restaurant. Administrators can add, delete, and modify users.

The project was developed using Spring Boot and Spring Security, and in the future, validation will be added.

## Future Enhancements
In the future, I plan to add validation to the application to ensure that users enter the correct information. 

## Users 
Users are owners of restaurants and they can change their status from "closed" to "open" and by Spring security after authorizatiom they will be moved to /user/{restaurant.id} 

## Admin
Administrator will add new users with their restaurants and they can change information about users in /admin/users


## PostgreSQL 
This is SQL code 


```sh
CREATE TABLE Person(
              id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
              username varchar,
              password varchar,
              role varchar
          );
CREATE TABLE Restaurant(
              id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
              name varchar,
              description varchar,
              address varchar,
              is_open boolean,
              latitude numeric(10,6),
              longitude numeric(10,6)
              imageadress varchar
              person_id int UNIQUE REFERENCES Person (id) ON DELETE CASCADE
          
          )
```
