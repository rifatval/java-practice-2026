drop table product;

create table product (
	id serial unique not null,
    name char(20) not null,
    cost integer check(cost > -1)
);

insert into product (name, cost) values ('banana', 44);
