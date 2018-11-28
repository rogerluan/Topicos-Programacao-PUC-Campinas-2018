# Donations

## Database

### Cheatsheet

| Description         	| Command       	|
|----------------------	|---------------	|
| Access Database    	| psql donation 	|
| Change to Database 	| \c donation   	|
| Quitting            	| \q            	|

### Implementation

The queries used to create the tables and insert test data.

```
set timezone TO 'GMT';

CREATE TABLE donations(
	id serial PRIMARY KEY,
	title VARCHAR (128) NOT NULL,
	description VARCHAR (1024),
	contact VARCHAR (355),
	city VARCHAR (50) NOT NULL,
	state VARCHAR (2) NOT NULL,
	address VARCHAR (128),
	created_on TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);
```

```
CREATE TABLE comments(
	id serial PRIMARY KEY,
	donation_id integer NOT NULL,
	comment VARCHAR (1024) NOT NULL,
	created_on TIMESTAMP NOT NULL DEFAULT NOW(),
	FOREIGN KEY (donation_id) REFERENCES donations (id) on delete cascade
);
```

```
insert into donations (title, city, state) values ('pans', 'campinas', 'sp');
insert into comments (donation_id, comment) values (1, 'this is a comment');
delete from donations where donations.id = 1;
```

## Web Service

- [x] DBOS
    - [x] Donation
    - [x] Comment
- [x] DBAS
    - [x] Donations
    - [x] Comments
- [x] Endpoints
    - [x] POST /donations
    - [x] GET /donations
    - [x] POST /donations/id/comments
    - [x] GET /donations/id/comments
    - [x] GET /donations/id // Not being used actually

To test:

GET http://localhost:8080/JSONRestWebServiceExample/JavaCodeGeeks/donations/

GET http://localhost:8080/JSONRestWebServiceExample/JavaCodeGeeks/donations/2

GET http://localhost:8080/JSONRestWebServiceExample/JavaCodeGeeks/donations/2/comments

POST http://localhost:8080/JSONRestWebServiceExample/JavaCodeGeeks/donations/2/comments

## Web App

- [x] Implement webpage with a form to add donations

## Android App

- [x] Model
    - [x] Donation
    - [x] Comment
- [x] Server
    - [x] GET /donations
    - [x] GET /donations/id/comments
    - [x] POST /donations/id/comments
- [x] UI
    - [x] Donations list, with no pagination
    - [x] Donations local search
    - [x] Donation details screen
        - [x] Showing comments
        - [x] Allow adding new comments


## Next Steps

- [ ] Implement auto-expiring records (donations), deleting donations when they're 1 week old.

Ref.: SQLServer e Azure SQL Database have the concept of Jobs, but PostgreSQL doesn’t.
To implement it, we need a procedure and pgAdmin 4 to schedule the jobs: https://www.postgresql.org/ftp/pgadmin/pgadmin4/v3.5/macos/

## References

https://docs.oracle.com/cd/E19798-01/821-1841/6nmq2cp1v/index.html
https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/MessageBodyReader.html
https://stackoverflow.com/questions/8194408/how-to-access-parameters-in-a-restful-post-method
https://www.oracle.com/technetwork/articles/javaee/jax-rs-159890.html
https://stackoverflow.com/questions/37814857/retrofit-2-with-only-form-data
https://examples.javacodegeeks.com/enterprise-java/rest/java-json-restful-web-service-example/?fbclid=IwAR2GuxvFroRtB-Vi1f-mGEwOW4D1C3HgcFZt6HNBfjC99_laqf-M4Vl5-ww
