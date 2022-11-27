package pr4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import pr4.model.Film;
import pr4.model.Ticket;
import pr4.repository.FilmRepository;
import pr4.repository.TicketRepository;
import pr4.service.FilmService;
import pr4.service.TicketService;
import reactor.core.publisher.Mono;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    FilmService filmService;

    @Autowired
    TicketService ticketService;

    @Test
    public void createRandomTest() {
        Mono<Ticket> ticket = ticketService.createRandomTicket();
        System.out.println(ticket.block().toString());
        assertEquals(ticket, ticketService.getTicketById(ticket.block().getId()));
    }
}
