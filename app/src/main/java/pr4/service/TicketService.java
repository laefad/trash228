package pr4.service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pr4.model.Film;
import pr4.model.Ticket;
import pr4.repository.TicketRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FilmService filmService;

    @Transactional(readOnly = true)
    public Mono<Ticket> getTicketById(long id) {
        return ticketRepository.findById(id);
    }

    public Mono<Ticket> createRandomTicket() {
        return filmService.createRandomFilm().flatMap(
            (Film f) -> ticketRepository.save(Ticket.builder()
                .filmId(f.getId())
                .price(228)
                .sessionDate(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt()))
                .build()
            )
        );
    }

    public Mono<Ticket> createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public Flux<Ticket> getAllTickers() {
        return ticketRepository.findAll().flatMap(
            (Ticket t) -> filmService.getFilmById(t.getFilmId()).map(
                (Film f) -> t.withFilm(f)
            )
        );
    }
}
