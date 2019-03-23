create table "Projects"
(
  id   serial not null
    constraint projects_pk
      primary key,
  name varchar(155)
);

insert into "Projects" (name) values ('project22');
insert into "Projects" (name) values ('project34');
insert into "Projects" (name) values ('project45');
