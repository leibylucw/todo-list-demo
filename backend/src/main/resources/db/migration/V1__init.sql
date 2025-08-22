-- users table
create table if not exists users (
	id bigserial primary key,
	name varchar(100) not null
);

-- tasks table
create table if not exists tasks (
	id bigserial primary key,
	name varchar(200) not null,
	description text,
	user_id bigint not null references users(id) on delete cascade,
	created_at timestamptz not null default now(),
	updated_at timestamptz not null default now()
);
