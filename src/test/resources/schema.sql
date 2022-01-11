drop table if exists Role_User;
drop table if exists Bid;
drop table if exists Item;
drop table if exists Usr;
drop table if exists Roles;

create table Usr(
	id bigserial not null primary key,
	firstname varchar(20) not null,
	lastname varchar(20) not null,
	email varchar(255) unique not null,
	gender varchar(10) not null,
	"PASSWORD" varchar (255) not null
);

create table Role_User(
	user_id bigint not null,
	name varchar(50) not null,
	foreign key (user_id) references Usr(id),
	primary key (user_id, name)
);

create table Item(
	id bigserial not null primary key,
	title varchar(30) not null,
	description varchar(200) not null,
	seller varchar(24) not null,
	start_price numeric(50, 2) not null,
	bid_inc numeric(50, 2) not null,
	best_offer numeric(50, 2) not null,
	bidder varchar(24) not null,
	stop_date varchar(100) not null,
	user_id bigint not null,
	image bytea,
	foreign key (user_id) references Usr(id)
);

create table Bid(
	id bigserial not null primary key,
	amount numeric(50, 2) not null,
	time_stamp varchar(255) not null,
	user_id bigint not null,
	item_id bigint not null,
	foreign key (user_id) references Usr(id),
	foreign key (item_id) references Item(id),
	unique(user_id, item_id, amount)
);