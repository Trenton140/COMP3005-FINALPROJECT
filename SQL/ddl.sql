CREATE TABLE Owner
(
  password VARCHAR(20) NOT NULL,
  id VARCHAR(20) NOT NULL,
  first_name VARCHAR(15) NOT NULL,
  last_name VARCHAR(15) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Book_User
(
  password VARCHAR(20) NOT NULL,
  email VARCHAR(40) NOT NULL,
  card_expiration NUMERIC(4, 0) NOT NULL,
  card_number NUMERIC(20, 0) NOT NULL,
  card_cvv NUMERIC(10, 0) NOT NULL,
  phone NUMERIC(10, 0) NOT NULL,
  first_name VARCHAR(15) NOT NULL,
  last_name VARCHAR(15) NOT NULL,
  id VARCHAR(20) NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Publisher
(
  email VARCHAR(40) NOT NULL,
  account NUMERIC(7, 0) NOT NULL,
  transit NUMERIC(5, 0) NOT NULL,
  institution NUMERIC(3, 0) NOT NULL,
  id INT NOT NULL,
  first_name VARCHAR(15) NOT NULL,
  last_name VARCHAR(15) NOT NULL,
  house INT NOT NULL,
  street VARCHAR(20) NOT NULL,
  city VARCHAR(20) NOT NULL,
  postal VARCHAR(7) NOT NULL,
  store_contact VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (store_contact) REFERENCES Owner(id)
);

CREATE TABLE Book_Order
(
  number NUMERIC(15,0) NOT NULL,
  delivery_date DATE,
  ship_date DATE,
  total_price NUMERIC(12, 2) NOT NULL,
  shipping_house INT NOT NULL,
  shipping_street VARCHAR(30) NOT NULL,
  shipping_city VARCHAR(30) NOT NULL,
  shipping_postal VARCHAR(7) NOT NULL,
  billing_house INT NOT NULL,
  billing_street VARCHAR(30) NOT NULL,
  billing_city VARCHAR(30) NOT NULL,
  billing_postal VARCHAR(7) NOT NULL,
  user_id VARCHAR(20) NOT NULL,
  PRIMARY KEY (number),
  FOREIGN KEY (user_id) REFERENCES Book_User(id)
);

CREATE TABLE Phone
(
  phone NUMERIC(10, 0) NOT NULL,
  publisher_id INT NOT NULL,
  PRIMARY KEY (phone, publisher_id),
  FOREIGN KEY (publisher_id) REFERENCES Publisher(id)
);

CREATE TABLE Book
(
  name VARCHAR(100) NOT NULL,
  percentage NUMERIC(5, 2) NOT NULL,
  price NUMERIC(10, 2) NOT NULL,
  pages INT NOT NULL,
  isbn NUMERIC(13, 0) NOT NULL,
  stock INT NOT NULL,
  publisher_id INT NOT NULL,
  PRIMARY KEY (isbn),
  FOREIGN KEY (publisher_id) REFERENCES Publisher(id)
);

CREATE TABLE Genre
(
  genre VARCHAR(30) NOT NULL,
  book_isbn NUMERIC(13, 0) NOT NULL,
  PRIMARY KEY (genre, book_isbn),
  FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);

CREATE TABLE Author
(
  first_name VARCHAR(15) NOT NULL,
  last_name VARCHAR(15) NOT NULL,
  book_isbn NUMERIC(13, 0) NOT NULL,
  PRIMARY KEY (first_name, last_name, book_isbn),
  FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);

CREATE TABLE sold
(
  order_number NUMERIC(15,0) NOT NULL,
  book_isbn NUMERIC(13, 0) NOT NULL,
  PRIMARY KEY (order_number, book_isbn),
  FOREIGN KEY (order_number) REFERENCES Book_Order(number),
  FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);