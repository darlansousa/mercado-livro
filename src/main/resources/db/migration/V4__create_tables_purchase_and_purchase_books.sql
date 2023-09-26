CREATE TABLE purchases (
  id INT AUTO_INCREMENT NOT NULL,
  customer_id INT NOT NULL,
  nfe VARCHAR(255) NULL,
  price DECIMAL(10,2) NOT NULL,
  created_at DATETIME NOT NULL,
  FOREIGN KEY(customer_id) REFERENCES customers(id),
  CONSTRAINT pk_purchases PRIMARY KEY (id)
);

CREATE TABLE purchase_books (
    purchase_id INT NOT NULL,
    book_id INT NOT NULL,
    FOREIGN KEY(purchase_id) REFERENCES purchases(id),
    FOREIGN KEY(book_id) REFERENCES books(id),
    CONSTRAINT pk_purchase_books PRIMARY KEY (purchase_id, book_id)
);