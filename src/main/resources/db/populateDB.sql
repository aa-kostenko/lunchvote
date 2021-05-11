TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users (name, email, password)
VALUES ('Admin1', 'admin1@gmail.com', 'admin1'),
       ('Admin2', 'admin2@gmail.com', 'admin2'),
       ('User1', 'user1@yandex.ru', 'password1'),
       ('User2', 'user2@yandex.ru', 'password2'),
       ('User3', 'user3@yandex.ru', 'password3');

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 1),
       ('ADMIN', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5);

INSERT INTO restaurants(name)
VALUES ('Бумбараш'),
       ('Диканька');

INSERT INTO lunch_menu_items(name, restaurant_id, menu_date, price)
VALUES ('Борщ по-мегрельски', 1, '2021-01-30', 280.00),
       ('Стейк из семги на углях', 1, '2021-01-30', 660.00),
       ('Картошечка жареная с вешенками', 1, '2021-01-30', 160.00),

       ('Грузинский рыбный суп', 1, '2021-01-31', 380.00),
       ('Шашлык из курицы', 1, '2021-01-31', 260.00),
       ('Картофельное пюре', 1, '2021-01-31', 160.00),

       ('Борщ классический', 2, '2021-01-30', 385.00),
       ('Котлета по-киевски', 2, '2021-01-30', 445.00),
       ('Оливье', 2, '2021-01-30', 375.00),

       ('Лапша домашняя', 2, '2021-01-31', 295.00),
       ('Пельмени', 2, '2021-01-31', 325.00),
       ('Селедка под шубой', 2, '2021-01-31', 365.00);

INSERT INTO votes(user_id, date_time, restaurant_id)
VALUES (3, '2021-01-30 09:20:55', 1),
       (4, '2021-01-30 10:45:15', 1),
       (5, '2021-01-30 08:05:44', 2),
       (3, '2021-01-31 08:22:33', 1),
       (4, '2021-01-31 09:57:02', 2),
       (5, '2021-01-31 08:07:01', 2);
