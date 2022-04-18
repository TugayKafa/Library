package com.endava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "h_assignment")
public class AssignmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "h_user_id", nullable = false)
    @NotNull
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "h_assignment_books", joinColumns = @JoinColumn(name = "h_assignment_id"))
    @Column(name = "h_book_id")
    private List<Long> assignmentBooksHistory;

    @Column(name = "h_assignment_date", nullable = false)
    private LocalDate assignmentDateHistory;

    @Column(name = "h_return_date", nullable = false)
    private LocalDate returnDateHistory;

    @Column(name = "h_period_in_weeks", nullable = false)
    private Integer periodInWeeksHistory;

}
