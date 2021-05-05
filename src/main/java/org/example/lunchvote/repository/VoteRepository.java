package org.example.lunchvote.repository;

import org.example.lunchvote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v WHERE v.dateTime =:date ORDER BY v.restaurant.name ASC, v.dateTime DESC")
    List<Vote> getAllOnDate(@Param("date") LocalDate date);

    default List<Vote> getAllOnCurrentDate(){
        return getAllOnDate(LocalDate.now());
    }
}
