DROP TABLE votes IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE menus IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;

CREATE TABLE users
(
  id                INTEGER IDENTITY,
  name              VARCHAR(255)            NOT NULL,
  email             VARCHAR(255)            NOT NULL,
  password          VARCHAR(255)            NOT NULL,
  registered        DATE DEFAULT today()    NOT NULL,
  enabled           BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_roles
(
  user_id           INTEGER                 NOT NULL,
  role              VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id                INTEGER IDENTITY,
  name              VARCHAR(255)            NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE menus
(
  id                INTEGER IDENTITY,
  restaurant_id     INTEGER                 NOT NULL,
  date              DATE DEFAULT today()    NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menus_unique_restaurant_date_idx ON menus (restaurant_id, date);

CREATE TABLE dishes
(
  id                INTEGER IDENTITY,
  menu_id           INTEGER                 NOT NULL,
  name              VARCHAR(255)            NOT NULL,
  price             INTEGER                 NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
  id                INTEGER IDENTITY,
  user_id           INTEGER                 NOT NULL,
  restaurant_id     INTEGER                 NOT NULL,
  date              DATE DEFAULT today()    NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx ON votes (user_id, date);