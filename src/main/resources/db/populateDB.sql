DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO users (id, name, email, password) VALUES
  (0, 'User', 'user@gmail.com', 'password'),
  (1, 'Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 0),
  ('ROLE_ADMIN', 1);

INSERT INTO restaurants (id, name) VALUES
  (0, 'KFC'),
  (1, 'Burger King'),
  (2, 'Пузата Хата');

INSERT INTO menus (id, date, restaurant_id) VALUES
  (0, TODAY(), 0), (1, '2017-09-29', 0),
  (2, TODAY(), 1), (3, '2017-09-29', 1),
  (4, TODAY(), 2), (5, '2017-09-29', 2);

INSERT INTO dishes (id, name, price, menu_id) VALUES
  (0, 'Острые крылышки', 4000, 0),    (1, 'Сочная ножка', 3000, 0),
  (2, 'Крылышки', 2000, 1),           (3, 'Ножка', 1000, 1),
  (4, 'Двойной чизбургер', 2500, 2),  (5, 'Вкусный гамбургер', 1500, 2),
  (6, 'Чизбургер', 2000, 3),          (7, 'Гамбургер', 1000, 3),
  (8, 'Красный борщ', 9999, 4),       (9, 'Домашнаяя колбаска', 5555, 4),
  (10, 'Борщ', 9000, 5),              (11, 'Колбаска', 5000, 5);

INSERT INTO votes (id, user_id, restaurant_id, date) VALUES
  (0, 0, 0, TODAY()), (1, 0, 0, '2017-09-29'),
  (2, 1, 0, TODAY()), (3, 1, 0, '2017-09-29');