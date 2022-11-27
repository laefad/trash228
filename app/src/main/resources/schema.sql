DROP TABLE IF EXISTS film, ticket CASCADE;

CREATE TABLE film (
    id bigserial, 
    name VARCHAR(255),
    description VARCHAR(4096), 
    release_date TIMESTAMP,
    CONSTRAINT film_pkey PRIMARY KEY (id)
);

CREATE TABLE ticket (
    id bigserial, 
    film_id integer,
    price DOUBLE PRECISION, 
    session_date TIMESTAMP,
    CONSTRAINT ticket_pkey PRIMARY KEY (id),
    CONSTRAINT "ticket_to_film_FK" 
        FOREIGN KEY (film_id)
        REFERENCES public.film (id) 
        MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);
