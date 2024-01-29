create table if not exists book (
    id varchar constraint book_pk primary key default uuid_generate_v4(),
    author varchar not null,
    title varchar not null,
    category varchar not null,
    file_link varchar not null
);