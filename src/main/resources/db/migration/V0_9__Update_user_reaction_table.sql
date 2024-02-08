ALTER TABLE book_reaction
    ALTER COLUMN id SET DEFAULT uuid_generate_v4();