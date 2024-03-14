# user-management-service
A Service to Manage Users

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