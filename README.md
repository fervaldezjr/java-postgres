# Dummy connection to PostgreSQL datebase using Java.

This repo has as a goal create a connection to PostgreSQL datebase using Java and add data into DDBB.

First of all you need a postgres server running.

I use [dbeaver](https://dbeaver.io/download/) to create the connection and interact with database.

## Create a new connection with the follow information:
```
host:     localhost
port:     55001 (check port number in your case)
username: postgres
password: postgrespw
```

## Execute a SQL script to create a new table:
```
CREATE TABLE person 
( 
    personid: INT, 
    firstname: VARCHAR(100), 
    secondname: VARCHAR(100),
    address: VARCHAR(100),
    city: VARCHAR(100)
);
```
