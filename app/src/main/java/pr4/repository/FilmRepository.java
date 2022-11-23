package pr4.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pr4.model.Film;

@Repository 
public interface FilmRepository extends ReactiveCrudRepository<Film, Long>{
    
}
