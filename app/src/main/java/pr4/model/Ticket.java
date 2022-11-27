package pr4.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import lombok.experimental.FieldDefaults;

@Table
@FieldDefaults(level = AccessLevel.PACKAGE)
@Builder
@Data
public class Ticket {
    @Id long id;

    double price;
    Instant sessionDate;
    long filmId;
    
    @With Film film;
}
