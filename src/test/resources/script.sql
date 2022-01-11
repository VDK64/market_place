insert into Usr (firstname, lastname, email, gender, "PASSWORD" ) values ('Ivan', 'Petrov', 'petro@mail.ru', 'MALE', '$2a$10$s659zNLhTWneMm827zZNduf1JkNoA0bU4y3iCFluoU7RJKiEWZQSS');
insert into Usr (firstname, lastname, email, gender, "PASSWORD" ) values ('Boris', 'Vasiliev', 'borya@mail.ru', 'MALE', '$2a$10$s659zNLhTWneMm827zZNduf1JkNoA0bU4y3iCFluoU7RJKiEWZQSS');
insert into Usr (firstname, lastname, email, gender, "PASSWORD" ) values ('Aleks', 'Semenov', 'lex64@yandex.ru', 'MALE', '$2a$10$s659zNLhTWneMm827zZNduf1JkNoA0bU4y3iCFluoU7RJKiEWZQSS');
insert into Usr (firstname, lastname, email, gender, "PASSWORD" ) values ('Andrey', 'Koshelev', 'koshel@yahoo.ru', 'MALE', '$2a$10$s659zNLhTWneMm827zZNduf1JkNoA0bU4y3iCFluoU7RJKiEWZQSS');
insert into Usr (firstname, lastname, email, gender, "PASSWORD" ) values ('Petr', 'Korolev', 'lucky@mail.ru', 'MALE', '$2a$10$s659zNLhTWneMm827zZNduf1JkNoA0bU4y3iCFluoU7RJKiEWZQSS');
insert into Usr (firstname, lastname, email, gender, "PASSWORD" ) values ('Fedor', 'Yagudin', 'fedok@mail.ru', 'MALE', '$2a$10$s659zNLhTWneMm827zZNduf1JkNoA0bU4y3iCFluoU7RJKiEWZQSS');

insert into Item (title, description, seller, start_price, bid_inc, best_offer, bidder, stop_date, user_id)
	values('Parrot', 'Very beautiful bird.', 'Mr.Petrov', 20.37, 5.41, 30.78, 'Mr.Vasiliev', '2025-09-17 11:15', 1);
insert into Item (title, description, seller, start_price, bid_inc, best_offer, bidder, stop_date, user_id)
	values('Golden Watch', 'I would sell a beautiful golden watch with golden bracelet.', 'Mr.Vasiliev', 150.34, 30.54, 180.62, 'Mr.Semenov', '2020-08-12 11:15', 2);
insert into Item (title, description, seller, start_price, bid_inc, best_offer, bidder, stop_date, user_id)
	values('Iphone X', 'This device will be a great gift to anyone. It has powerful processor, strong batterey and big big monitor with biiiig biiiig diagonaal. I actually trying to test long description view.',
'Mr.Yagudin', 300.57, 40.94, 315.64, 'Mr.Korolev', '2025-09-17 11:15', 6);

insert into Bid (amount, time_stamp, user_id, item_id) values (30.78, '08.17.2020', 2, 1);
insert into Bid (amount, time_stamp, user_id, item_id) values (180.62, '08.10.2020', 3, 2);
insert into Bid (amount, time_stamp, user_id, item_id) values (315.57, '08.16.2020', 5, 3);

insert into Role_User (user_id, name) values (1, 'USER');
insert into Role_User (user_id, name) values (1, 'ADMIN');
insert into Role_User (user_id, name) values (2, 'USER');
insert into Role_User (user_id, name) values (3, 'USER');
insert into Role_User (user_id, name) values (4, 'USER');
insert into Role_User (user_id, name) values (5, 'USER');
insert into Role_User (user_id, name) values (6, 'USER');