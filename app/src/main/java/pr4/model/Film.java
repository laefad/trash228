package pr4.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Film {

    @Id long id;

    String name;
    String description;
    Date releaseDate;

}
