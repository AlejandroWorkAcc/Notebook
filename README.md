### A small description
************************************************************************************************************************************************************************
- This project includes registration of users using Spring Security, placing the data of these users in the database. 
- The ability to create, delete and edit records by users in their "online notebook", which is implemented using the second table "notebookentry". 
- Working with the database is carried out using the implementation of Spring JPA interfaces (CRUD and JPArepositories). 
- Also included the ability to delete, add and edit user profiles by other users with the "DEVELOPER" level of rights.(not all data can be editable)

    Unfortunately, I do not have serious skills in front-end development.
    In the frontend, standard HTML tools were used, some Thymeleaf methods. Also Bootstrap v4 for a nicer look. 
************************************************************************************************************************************************************************

So, how it works... 
-------------------
##### You just need to create a new database(it can be either a MySQL database or, for example, u can use MAMP) and change the contents of the application.properties file as shown below.
**********************
application.properties
----------------------
*****************************************************************************************************************************************
#### spring.jpa.hibernate.ddl-auto=update
#### spring.datasource.url=jdbc:mysql://localhost:3306/{YOUR DATABASE NAME}?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
#### spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#### spring.datasource.username={YOUR DB LOGIN}
#### spring.datasource.password={YOUR DB PASSWORD}
*****************************************************************************************************************************************
#### if you have selected a different port in the database settings, the corresponding port value after the "localhost:" must be changed. 

thanks to the line - "spring.jpa.hibernate.ddl-auto=update" all tables will be created automaticaly.

- User data is added to DB upon registration, they notes is added in "notebookentry" table upon creating with user_id like foreign key on table "users" column "id".

### If u want to get DEVELOPERS rights, u need to create user with "DEVELOPER" in "role" column in "users" table. 
All new registered users are created with the default role "USER", the ability of the developer to change roles to other users has not yet been implemented. This will give you the necessary rights to view the list with all users, edit their profiles or delete them.

- Starting page adress is "http://localhost:8080/auth/login" in my case, because I was running the application on my computer.
  
### Requests with explanations are written below:
- /auth/login            --Starting page
- /auth/registration     --registration page or page for create new user if you are developer
- /notebook/notes        --all user notes (GET-request with an optional  String parameter "searched", that allows you to display records containing a specific string).
- /notebook/add          --page for create new note
- /notebook/{id}/edit    --page to edit existing note
- /notebook/{id}/remove  --request to delete note(displayed as a button)
- /users                 --for developers, page with list of all users 
- /users/{id}/edit       --to edit existing user (not yet available to users with rights "USER", will be added)
- /users/{id}/remove     --request to delete user (displayed as a button, BE CAREFUL, do not delete your user,  if it have developer rights, you will have to create a new one with the rights "DEVELOPER" manually through the database)
