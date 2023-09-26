CREATE TABLE books (
  id INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  status VARCHAR(255) NOT NULL,
  customer_id INT NOT NULL,
  FOREIGN KEY(customer_id) REFERENCES customers(id),
  CONSTRAINT pk_books PRIMARY KEY (id)
);