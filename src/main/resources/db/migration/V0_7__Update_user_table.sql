alter table if exists "user_table" alter column first_name drop not null ;
alter table if exists "user_table" alter column last_name drop not null ;
alter table if exists "user_table" alter column status drop not null;
alter table if exists "user_table" alter column email drop not null;
alter table if exists "user_table" add column if not exists firebase_id varchar;