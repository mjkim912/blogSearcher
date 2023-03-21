CREATE TABLE tb_top_searched
(
    id integer NOT NULL AUTO_INCREMENT,
   	keyword varchar(255),
    count integer,
    lastest_srch_date timestamp,
    primary key(id)
);