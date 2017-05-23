drop table if exists orders;

create table orders (
  id INTEGER IDENTITY PRIMARY KEY,
  item varchar(10),
  amount integer,
  description varchar(30),
  processed boolean
);