package pr4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
        return this.ticketService.createRandomTicket().then();
    };

    // request stream 
    @MessageMapping("allTickets")
    public Flux<String> allTickets() {
        return this.ticketService.getAllTickers().doOnNext(s -> System.out.println(s.toString())).map(s -> s.toString());
    }

    // channel 
    @MessageMapping("ticketCreationChannel")
    public Flux<String> ticketCreationChannel(Flux<String> input) {
        return input.flatMap(
            (String s) -> this.ticketService.createRandomTicket()
                .map(t -> t.toString())
        );
    }

    @MessageMapping("hello")
    public Mono<String> helloServer(String message) {
        return Mono.just(message)
            .map(msg -> message + " | Server says hello!");
    }
}
