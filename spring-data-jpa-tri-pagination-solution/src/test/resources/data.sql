delete from Contact;
delete from Address;
insert into Address (city, zipCode, id) values ('Coffeyville','67337', -1);
insert into Contact (address_id, age, firstname, lastname, id) values (-1,37,'Joe', 'Dalton', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-1,35,'William', 'Dalton', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-1,33,'Jack', 'Dalton', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-1,31,'Averell', 'Dalton', nextval('hibernate_sequence'));

insert into Address (city, zipCode, id) values ('Ash Grove','65604', -2);
insert into Contact (address_id, age, firstname, lastname, id) values (-2,78,'Ma', 'Dalton', nextval('hibernate_sequence'));

insert into Address (city, zipCode, id) values ('Los Angeles','90001', -3);
insert into Contact (address_id, age, firstname, lastname, id) values (-3,28,'Mickey', 'Mouse', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-3,25,'Minnie', 'Mouse', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-3,29,'Donald', 'Duck', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-3,27,'Daisy', 'Duck', nextval('hibernate_sequence'));

insert into Address (city, zipCode, id) values ('Los Angeles','91043', -4);
insert into Contact (address_id, age, firstname, lastname, id) values (-4,8,'Riri', 'Duck', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-4,8,'Fifi', 'Duck', nextval('hibernate_sequence'));
insert into Contact (address_id, age, firstname, lastname, id) values (-4,8,'Loulou', 'Duck', nextval('hibernate_sequence'));