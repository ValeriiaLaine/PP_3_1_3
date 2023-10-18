drop table if exists roles;
drop table if exists user_role;
drop table if exists users;
create table roles (id bigint not null auto_increment, role varchar(255) not null, primary key (id)) engine=InnoDB;
create table user_role (user_id bigint not null, role_id bigint not null) engine=InnoDB;
create table users (id bigint not null auto_increment, password varchar(255) not null, username varchar(20) not null, primary key (id)) engine=InnoDB;
alter table user_role add constraint FKt7e7djp752sqn6w22i6ocqy6q foreign key (role_id) references roles (id);
alter table user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users (id);

insert into roles (role) values ('ROLE_ADMIN');
insert into users (password, username) values ('$2a$12$EDZLIzaLH8FX.Sh9VH8wVO66l8tOJB/tkpODcTBfUCweWO1s2u4pi', 'admin');
insert into user_role (user_id, role_id) values (1, 1);
insert into roles (role) values ('ROLE_USER');
insert into users (password, username) values ('$2a$12$zAS2iEpQmkBl7P/7037BxeYjKNEYDdD/sUQ.aqvyQR3XotfFg8YOG', 'user');
insert into user_role (user_id, role_id) values (2, 2);