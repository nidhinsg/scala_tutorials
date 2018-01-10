# Tasks schema
 
# --- !Ups

CREATE SEQUENCE todo_id_seq;
CREATE TABLE todo (
    id integer NOT NULL DEFAULT nextval('todo_id_seq'),
    label varchar(255)
);
 
# --- !Downs
--
DROP TABLE todo;
DROP SEQUENCE todo_id_seq;
