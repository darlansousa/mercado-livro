CREATE TABLE customers (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   CONSTRAINT pk_customers PRIMARY KEY (id)
);