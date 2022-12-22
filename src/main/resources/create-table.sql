CREATE TABLE CUSTOMER_TST 
(
  ID NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1)
, FIRST_NAME VARCHAR2(20) 
, LAST_NAME VARCHAR2(20) 
, BIRTHDAY DATE 
, SALARY NUMBER(19,2)
, CONSTRAINT CUSTOMER_TST_PK PRIMARY KEY (ID) ENABLE 
);
