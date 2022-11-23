package pr4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pr4.model.Ticket;
import pr4.repository.TicketRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Transactional(readOnly = true)
    public Mono<Ticket> getTicketById(long id) {
        return ticketRepository.findById(id);
    }

    public Mono<Ticket> createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public Flux<Ticket> getAllTickers() {
        return ticketRepository.findAll();
    }
}
