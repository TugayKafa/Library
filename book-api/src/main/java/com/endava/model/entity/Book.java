package com.endava.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cover_image_url", nullable = false, unique = true)
    private String coverImageUrl;

    @Column(name = "isbn", nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publisher", nullable = false)
    private String publisherName;


    @Column(name = "year_of_publication", nullable = false, precision = 4,columnDefinition = "varchar(4)")
    private Short yearOfPublication;

    @ManyToMany(targetEntity = Author.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id",
                    referencedColumnName = "id"))
    private List<Author> authors;


    @Column(name = "date_added", nullable = false, columnDefinition = "varchar(10)")
    private LocalDate dateAdded;


    @Column(name = "genre")
    @ManyToMany(targetEntity = Genre.class)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id",
                    referencedColumnName = "id"))
    private Set<Genre> genres;


    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "tag")
    @ManyToMany(targetEntity = Tag.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "id"))
    private Set<Tag> tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "language",columnDefinition = "varchar(15)")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",columnDefinition = "varchar(15)")
    private Status status = Status.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason_of_deactivation",columnDefinition = "varchar(15)")
    private Reason reason;

    @Column(name = "date_of_Deactivation",columnDefinition = "varchar(10)")
    private LocalDate dateOfDeactivation;

    @Lob
    private String description;

}
