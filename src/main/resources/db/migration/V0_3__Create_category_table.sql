create table if not exists category (
    id varchar constraint category_pk primary key,
    name varchar not null unique
);

alter table "book" rename column "category" to "category_id";
alter table "book" add constraint fk_book_category foreign key ("category_id") references "category" ("id");