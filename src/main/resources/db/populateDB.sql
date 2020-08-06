DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, make_timestamptz(2015, 5, 30, 10, 0, 0), 'Завтрак', 500),
  (100000, make_timestamptz(2015, 5, 30, 13, 0, 0), 'Обед', 1000),
  (100000, make_timestamptz(2015, 5, 30, 20, 0, 0), 'Ужин', 500),
  (100000, make_timestamptz(2015, 5, 31, 10, 0, 0), 'Завтрак', 1000),
  (100000, make_timestamptz(2015, 5, 31, 13, 0, 0), 'Обед', 500),
  (100000, make_timestamptz(2015, 5, 31, 20, 0, 0), 'Ужин', 510),
  (100000, make_timestamptz(2015, 6, 1, 9, 0, 0), 'Завтрак', 900),
  (100000, make_timestamptz(2015, 6, 1, 13, 0, 0), 'Обед', 610),
  (100000, make_timestamptz(2015, 6, 1, 18, 0, 0), 'Ужин', 480),
  (100001, make_timestamptz(2015, 5, 30, 10, 30, 0), 'Завтрак админа', 450),
  (100001, make_timestamptz(2015, 5, 30, 13, 30, 0), 'Обед админа', 1000),
  (100001, make_timestamptz(2015, 5, 30, 19, 0, 0), 'Ужин админа', 500);
