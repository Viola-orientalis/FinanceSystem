create table if not exists member
( userid varchar(255),
password varchar(255) not null,
username varchar(255) not null,
role varchar(255) default 'USER' not null,
primary key(userid)
);

create table if not exists account
( id bigint auto_increment,
userid varchar(255) not null,
account_number varchar(255) not null,
owner_name varchar(255) not null,
balance bigint default 0 not null,
created_at timestamp default current_timestamp,
primary key(id)
);

create table if not exists transaction_history
( id bigint auto_increment,
account_id bigint not null,
type varchar(50) not null,
amount bigint not null,
balance_before bigint not null,
balance_after bigint not null,
created_at timestamp default current_timestamp,
primary key(id)
);