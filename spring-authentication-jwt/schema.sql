create table u_user (id bigint not null, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), role varchar(255), primary key (id));
create sequence u_user_seq start with 1 increment by 50;
create table event_publication (completion_date timestamp(6) with time zone, publication_date timestamp(6) with time zone, id uuid not null, event_type varchar(255), listener_id varchar(255), serialized_event varchar(255), primary key (id));
