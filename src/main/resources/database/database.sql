create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create table if not exists user
(
	userid integer not null
		constraint users_pk
			primary key,
	fullname varchar(50) not null,
	mail varchar(50) not null,
	password varchar(255),
	role integer not null
);

alter table user owner to postgres;

create unique index if not exists users_userid_uindex
	on user (userid);

create table if not exists week
(
	userid integer not null
		constraint week_users_userid_fk
			references user,
	day integer not null,
	hours integer not null,
	constraint week_pk
		primary key (userid, day)
);

alter table week owner to postgres;

create table if not exists session
(
	sessionid integer not null
		constraint session_pk
			primary key,
	userid integer not null
		constraint session_users_userid_fk
			references user,
	cookiehash varchar(255) not null,
	timestamp timestamp not null
);

alter table session owner to postgres;

create unique index if not exists session_sessionid_uindex
	on session (sessionid);

create table if not exists project
(
	projectid integer not null
		constraint project_pk
			primary key,
	name varchar(50) not null,
	startdate date not null,
	duration integer not null,
	closed boolean not null
);

alter table project owner to postgres;

create unique index if not exists project_projectid_uindex
	on project (projectid);

create table if not exists projectmembership
(
	isscrummaster boolean not null,
	userid integer not null
		constraint projectmembership_users_userid_fk
			references "user",
	projectid integer not null
		constraint projectmembership_project_projectid_fk
			references project,
	constraint projectmembership_pk
		primary key (projectid, userid)
);

alter table projectmembership owner to postgres;

create table if not exists sprint
(
	sprintid integer not null
		constraint sprint_pk
			primary key,
	projectid integer not null
		constraint sprint_project_projectid_fk
			references project,
	startdate date not null,
	closed boolean not null
);

alter table sprint owner to postgres;

create unique index if not exists sprint_sprintid_uindex
	on sprint (sprintid);

create table if not exists declaration
(
	sprintid integer not null
		constraint declaration_sprint_sprintid_fk
			references sprint,
	userid integer not null
		constraint declaration_users_userid_fk
			references "user",
	hoursavailable integer not null,
	workneeded integer not null,
	comment varchar(255),
	constraint declaration_pk
		primary key (sprintid, userid)
);

alter table declaration owner to postgres;

