create extension if not exists "uuid-ossp";

do
$$
    begin
        if not exists(select from pg_type where typname = 'user_status') then
            create type "user_status" as enum ('ADMIN', 'CLIENT');
        end if;
    end
$$;
create table if not exists "user_table" (
    id varchar constraint user_pk primary key default uuid_generate_v4(),
    first_name varchar not null,
    last_name varchar not null,
    email varchar not null constraint user_email_unique unique,
    status user_status not null
);