DROP TABLE IF EXISTS todo;
CREATE TABLE todo
(
    id varchar(36) not null primary key ,
    description varchar(255) not null,
    created timestamp ,
    modified timestamp,
    completed boolean
);

DROP TABLE IF EXISTS todojpa;
CREATE TABLE todojpa
(
    id varchar(36) not null primary key ,
    description varchar(255) not null,
    created timestamp ,
    modified timestamp,
    completed boolean
)