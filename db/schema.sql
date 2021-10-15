create table roles(
                      id serial primary key,
                      role varchar(50) not null unique
);
create table persons(
                        id serial primary key,
                        login varchar(200) not null unique,
                        password varchar(50) not null,
                        role_id int not null references roles(id)
);
create table rooms(
                      id serial primary key,
                      theme varchar(300) not null
);
create table messages(
                         id serial primary key,
                         text varchar(5000) not null,
                         created timestamp without time zone not null default now(),
                         person_id int not null references persons(id),
                         room_id int not null references rooms(id)
);
