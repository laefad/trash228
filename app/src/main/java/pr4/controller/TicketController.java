package pr4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pr4.model.Ticket;
import pr4.service.TicketService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketController {

    @Autowired
    TicketService ticketService;

    // request - response 
    @MessageMapping("ticketById")
    public Mono<String> ticketById(String ticketId) {
        return this.ticketService.getTicketById(Long.parseLong(ticketId)).map(t -> t.toString());
    };

    // fire and forget
    @MessageMapping("createTicket")
    public Mono<Void> createTicket() {
        this.ticketService.createTicket(new Ticket());
        return Mono.empty(); 
    };

    // request stream 
    @MessageMapping("allTickets")
    public Flux<String> allTickets() {
        return this.ticketService.getAllTickers().map(s -> s.toString());
    }

    // channel 
    @MessageMapping("pingpong")
    public Flux<String> pingPong(Flux<String> input) {
        input.subscribe(
            (s) -> System.out.println("Recieved from channel: " + s)
        );
        return Flux.generate(
            () -> 1, // initial state
            (state, sink) -> {
                sink.next(state.toString());
                return state + 1;
            }
        );
    }

    @MessageMapping("hello")
    public Mono<String> helloServer(String message) {
        return Mono.just(message)
            .map(msg -> message + " | Server says hello!");
    }
}
