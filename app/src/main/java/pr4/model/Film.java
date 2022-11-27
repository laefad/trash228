package pr4.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Table
@FieldDefaults(level = AccessLevel.PACKAGE)
@Builder
@Data
public class Film {
    @Id long id;

    String name;
    String description;
    Instant releaseDate;
}
