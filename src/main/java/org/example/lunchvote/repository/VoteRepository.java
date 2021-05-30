package org.example.lunchvote.repository;

import org.example.lunchvote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id = :userId and v.dateTime >= :startDate and v.dateTime <= :endDate ORDER BY v.dateTime ASC")
    List<Vote> getAllBetweenForUser(@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDateTime, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> getForUser(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant  WHERE v.user.id = :userId ORDER BY v.dateTime ASC")
    List<Vote> getAllForUser(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.dateTime >= :startDate and v.dateTime <= :endDate ")
    List<Vote> getAllWithRestaurantsBetween(@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDateTime);

}
