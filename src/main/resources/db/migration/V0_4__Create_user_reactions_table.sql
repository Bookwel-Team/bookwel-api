do
$$
begin
    if not exists(select from pg_type where typname = 'reaction_status') then
        create type "reaction_status" as enum ('LIKE', 'DISLIKE');
    end if;
end
$$;

create table if not exists book_reaction(
    id varchar constraint book_reactions_pk primary key,
    user_id varchar references user_table(id),
    book_id varchar references book(id),
    reaction reaction_status not null,
    creation_datetime timestamp with time zone not null default now()
);

create table if not exists category_reaction(
    id varchar constraint category_reactions_pk primary key,
    user_id varchar references user_table(id),
    category_id varchar references category(id),
    reaction reaction_status not null,
    creation_datetime timestamp with time zone not null default now()
    );
