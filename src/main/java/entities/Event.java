package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//Generates getters for all fields, a useful toString method, and equals and hashCode methods.
// Also, it generates setters for all non-final fields.
@AllArgsConstructor
//Generates a constructor with 1 parameter for each field in your class.
@NoArgsConstructor
//Generates a no-arguments constructor.
@Table
@Builder
@Entity
public class Event {

    @Id
    private Long id;

    private String title;
    private String description;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;


}
