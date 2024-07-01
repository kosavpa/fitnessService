package owl.home.fitnessService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;


@Entity(name = "Message")
@Table(name = "MESSAGE")

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "SENDER")
    private String sender;

    @Column(name = "TEXT", length = 500)
    private String text;

    @Column(name = "PUBLICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date;
}