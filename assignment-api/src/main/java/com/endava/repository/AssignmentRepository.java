package com.endava.repository;

import com.endava.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByUserId(Long userId);

    @Query(value = "SELECT " +
            "    COUNT(ab.book_id) " +
            "    FROM assignment_db.assignment_books ab " +
            "    WHERE ab.book_id = :bookId ", nativeQuery = true)
    Long findBookById(@Param("bookId") Long bookId);

    @Query(value = "SELECT COUNT(DISTINCT(a.user_id)) FROM assignments a", nativeQuery = true)
    Integer countDistinctUserId();

    @Query(value = "SELECT asn.* " +
            "FROM assignment_db.assignments asn " +
            "JOIN assignment_db.assignment_books asb " +
            "ON asn.id = asb.assignment_id " +
            "WHERE asn.user_id = :userId " +
            "AND asb.book_id = :bookId"
            ,nativeQuery = true
    )
    Optional<Assignment> findByUserIdAndBookId(
            @Param("userId")Long userId,
            @Param("bookId")Long bookId);
}
