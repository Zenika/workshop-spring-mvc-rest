delete from User;
insert into User (activated, login, password, id) values (true, 'user1', 'secret', -1);
insert into User (activated, login, password, id) values (true, 'user2', 'secret', -2);
insert into User (activated, login, password, id) values (true, 'user2', 'secret', -3);
insert into User (activated, login, password, id) values (false, 'user3', 'secret', -4);