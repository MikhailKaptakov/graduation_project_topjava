DELETE FROM USER_ROLES;
DELETE FROM VOTES;
DELETE FROM MEALS;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO RESTAURANTS (ID, NAME, LAST_UPDATE)
VALUES ('1','NotActualRestaurant', '0001-01-01'),
       ('2','ActualRestaurant', NOW());


INSERT INTO MEALS (ID, NAME, MEAL_DAY, PRICE, RESTAURANT_ID)
VALUES ('3','old soup','0001-01-01', '500', '1'),
       ('4','old tea','0001-01-01', '10000', '1'),
       ('5','tea',NOW(), '5000', '2'),
       ('6','soup',NOW(), '30000', '2'),
       ('7','old soup','0001-01-01', '30000', '2'),
       ('8','old tea','0001-01-01', '5500', '2');

