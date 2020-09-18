create table users
(
	id int auto_increment,
	firstName varchar(255) not null,
	lastName varchar(255) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	schoolName varchar(255) not null,
	companyName varchar(255) null,
	createdAt timestamp default CURRENT_TIMESTAMP not null,
	updatedAt timestamp default CURRENT_TIMESTAMP not null,
	constraint users_pk
		primary key (id)
);

create unique index users_email_uindex
	on users (email);

