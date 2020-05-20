# BCP Spring JDBC Oracle

Template (starter) form spring-jdbc with Oracle DB

### Config Env:

Run `create-table.sql` script:

```sql
CREATE TABLE CUSTOMER_TST 
(
  ID NUMBER NOT NULL 
, FIRST_NAME VARCHAR2(50) 
, LAST_NAME VARCHAR2(50) 
, CONSTRAINT CUSTOMER_TST_PK PRIMARY KEY 
  (ID) ENABLE 
);
```

Configure database properties in `application.properties` file.

### Run Project

```console
mvn spring-boot:run
```