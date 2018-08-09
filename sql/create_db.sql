CREATE USER 'fp_user'@'localhost' IDENTIFIED BY 'fp_user';
CREATE DATABASE fp_db;
GRANT ALL PRIVILEGES ON fp_db.* TO 'fp_user'@'localhost';

USE fp_db
CREATE TABLE bh_session (
	id INT UNSIGNED PRIMARY KEY DEFAULT 1,
  username VARCHAR(50),
  password VARCHAR(50),
	client_id VARCHAR(50),
	client_secret VARCHAR(50),
	access_token VARCHAR(50),
	refresh_token VARCHAR(50),
	rest_token VARCHAR(50), 
	rest_url VARCHAR(100),
	expires_in SMALLINT UNSIGNED,
	created_time TIMESTAMP
);

INSERT INTO bh_session(id, username, password, client_id, client_secret)
VALUES (1, '*', '*', '*', '*');
