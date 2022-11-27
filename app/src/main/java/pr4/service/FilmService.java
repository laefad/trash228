package pr4.service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pr4.model.Film;
import pr4.repository.FilmRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmService {

    @Autowired
    FilmRepository filmRepository;

    @Transactional(readOnly = true)
    public Mono<Film> getFilmById(long id) {
        return filmRepository.findById(id);
    }

    public Mono<Film> createRandomFilm() {
        return filmRepository.save(Film.builder()
            .description("Some description")
            .name("Some name")
            .releaseDate(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt()))
            .build()
        );
    }

    public Mono<Film> createFilm(Film film) {
        return filmRepository.save(film);
    }

    @Transactional(readOnly = true)
    public Flux<Film> getAllFilms() {
        return filmRepository.findAll();
    }
}
