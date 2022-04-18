package com.endava.repository;

import com.endava.model.AssignmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentHistoryRepository extends JpaRepository<AssignmentHistory, Long> {

    @Query(value = "SELECT COUNT(asb.h_book_id) AS numberOfBooks " +
            "FROM assignment_db.h_assignment_books asb " +
            "JOIN h_assignment AS ash ON ash.id = asb.h_assignment_id " +
            "WHERE ash.h_return_date " +
            "BETWEEN :returnDate AND :todayDate " +
            "AND ash.h_user_id = :userId " +
            "AND asb.h_book_id IN :booksToAssign",
            nativeQuery = true
    )
    Long findAssignedBookHistoryCountByReturnDateAndUserId(
            @Param("returnDate") LocalDate returnDate,
            @Param("todayDate") LocalDate todayDate,
            @Param("userId") Long userId,
            @Param("booksToAssign") List<Long> booksIdsToAssign
    );
}
