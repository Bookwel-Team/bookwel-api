do
$$
begin
    if  exists(select from pg_type where typname = 'reaction_status') then
        alter type "reaction_status" add value 'UNSET' before 'LIKE';
    end if;
end
$$;