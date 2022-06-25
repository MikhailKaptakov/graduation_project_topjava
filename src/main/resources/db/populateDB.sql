DELETE FROM USER_ROLES;
DELETE FROM VOTES;
DELETE FROM MEALS;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;

INSERT INTO RESTAURANTS (ID, NAME, LAST_UPDATE)
VALUES ('1','NotActualRestaurant', '0001-01-01'),
       ('2','ActualRestaurant', NOW());


INSERT INTO MEALS (ID, NAME, MEAL_DATE, PRICE, RESTAURANT_ID)
VALUES ('20','old soup','0001-01-01', '500', '1'),
       ('21','old tea','0001-01-01', '10000', '1'),
       ('22','tea',NOW(), '5000', '2'),
       ('23','soup',NOW(), '30000', '2'),
       ('24','old soup','0001-01-01', '30000', '2'),
       ('25','old tea','0001-01-01', '5500', '2');

INSERT INTO USERS (ID, NAME, EMAIL, PASSWORD)
VALUES ('100','User', 'userMail@test.test', 'userPassword'),
       ('101','Admin', 'adminMail@test.test', 'adminPassword');

INSERT INTO USER_ROLES (USER_ID, ROLE)
VALUES (100, 'USER'),
       (101, 'USER'),
       (101, 'ADMIN');

INSERT INTO VOTES (ID, USER_ID, RESTAURANT_ID, VOTE_DATE)
VALUES ('150','100', '1', '1000-10-10'),
       ('151','100', '1', '2000-10-10'),
       ('152','100', '2',  NOW()),
       ('153','100', '2', '2000-10-12'),
       ('154','101', '1', '0900-10-10'),
       ('155','101', '1', '2000-10-10'),
       ('156','101', '2', NOW()),
       ('157','101', '2', '2000-10-15');
