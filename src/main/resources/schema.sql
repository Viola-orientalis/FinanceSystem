
USE testdb;

CREATE TABLE IF NOT EXISTS member (
  userid VARCHAR(255) NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS account (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_number VARCHAR(20) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  balance BIGINT DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES member(userid)
);

CREATE TABLE IF NOT EXISTS `transaction` (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT NOT NULL,
  type VARCHAR(10) NOT NULL,
  amount BIGINT NOT NULL,
  balance_before BIGINT NOT NULL,
  balance_after BIGINT NOT NULL,
  created_at DATETIME DEFAULT NOW(),
  FOREIGN KEY (account_id) REFERENCES account(id)
);

INSERT INTO member (userid, password, username, role)
VALUES ('user1', '1234', 'Demo User', 'USER');

INSERT INTO account (account_number, user_id, balance)
VALUES ('123-456-789', 'user1', 1000000);
