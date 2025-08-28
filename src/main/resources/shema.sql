create table if not exists dictionary_entries (
    id serial primary key,
    key varchar(255) not null,
    dictionary_type varchar(50) not null,
    unique(key, dictionary_type)
);

create table if not exists dictionary_values (
    id serial primary key,
    entry_id bigint not null references dictionary_entries(id) on delete cascade,
    value varchar(255) not null
);

create index if not exists idx_entries_key on dictionary_entries(key);
create index if not exists idx_entries_type on dictionary_entries(dictionary_type);
create index if not exists idx_values_entry_id on dictionary_values(entry_id);
create index if not exists idx_values_value on dictionary_values(value);