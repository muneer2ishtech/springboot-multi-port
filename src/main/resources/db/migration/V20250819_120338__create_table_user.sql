CREATE TABLE t_user (
  id             BIGSERIAL     NOT NULL,
  email          VARCHAR(255)  NOT NULL UNIQUE,
  password_hash  VARCHAR(255)  NOT NULL,
  first_name     VARCHAR(255)  NOT NULL,
  last_name      VARCHAR(255)  NOT NULL,
  PRIMARY KEY (id)
);