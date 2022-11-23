DROP TABLE IF EXISTS film, ticket CASCADE;

CREATE TABLE film (
    id INT PRIMARY KEY, 
    name VARCHAR(255),
    description VARCHAR(4096), 
    releaseDate DATE
);

CREATE TABLE ticket (
    id INT PRIMARY KEY, 
    film_id INT NOT NULL,
    price DOUBLE PRECISION, 
    sessionDate DATE
);

ALTER TABLE ticket 
    ADD CONSTRAINT fk_film
    FOREIGN KEY (film_id) 
    REFERENCES film(id);

INSERT INTO public.film (id, name, description, releasedate) VALUES 
    (1, 'Gatsby', 'Great Gatsby', NULL); 

INSERT INTO public.ticket (id, film_id, price, sessiondate) VALUES 
    (1, 1, 22, NULL),
    (2, 1, 23, NULL);
