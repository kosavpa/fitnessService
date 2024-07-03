package owl.home.fitnessService.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;


@Entity(name = "Message")
@Table(name = "messages")

@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    public UUID id;

    @Column(name = "SENDER")
    private String sender;

    @Column(name = "TEXT", length = 500)
    private String text;

    @Column(name = "PUBLICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date;

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}