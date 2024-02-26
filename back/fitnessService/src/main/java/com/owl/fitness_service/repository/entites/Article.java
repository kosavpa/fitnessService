package com.owl.fitness_service.repository.entites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Length;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.UUID;


@Entity(name = "Article")
@Table(name = "ARTICLES")

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "HEADER")
    public String header;

    @Column(name = "ANONS")
    public String anons;

    @Column(name = "TEXT", columnDefinition = "TEXT")
    public String text;

    @Column(name = "RELATIVE_IMG_PATH")
    public String relativeImgPath;

    @Column(name = "PUBLICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Timestamp date;
}