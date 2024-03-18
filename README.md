User Management Service Modules
    API
    Client
    Admin
    MDB Bootstrap Template
    MDB React Template
    MDB Angular Template
    MDB Vue Template
    Thymeleaf Template


SQL QUERY to Create Database
```
create database userservicedb;
```

SQL QUERY to Create User for Service
```
create user userservice@"%" identified by "";
```

SQL QUERY to Grant Privilages of Database to the new DB User
```
grant all privileges on userservicedb.* to userservice;
```

Maven Command to run DB Migration using Liquibase
```
mvn liquibase:updateTestingRollback
```