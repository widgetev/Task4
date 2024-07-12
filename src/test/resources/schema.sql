CREATE DATABASE postgres_tst;

create table Users (
    id SERIAL PRIMARY KEY
    , username VARCHAR
    , fio VARCHAR
);

create table loginsHistory (
    id SERIAL PRIMARY KEY
    , user_id INTEGER REFERENCES Users(id)
    , access_date TIMESTAMP
    , application VARCHAR
);