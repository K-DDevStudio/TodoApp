package entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;


}
