package pr4.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pr4.model.Ticket;

@Repository
public interface TicketRepository extends ReactiveCrudRepository<Ticket, Long>{
    
}
