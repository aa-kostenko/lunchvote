package org.example.lunchvote.repository;

import org.example.lunchvote.model.LunchMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LunchMenuItemRepository extends JpaRepository<LunchMenuItem, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM LunchMenuItem i WHERE i.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT mi FROM LunchMenuItem mi WHERE mi.restaurant.id=:restaurantId "
            + " and mi.menuDate >= :startDate and mi.menuDate <= :endDate")
    List<LunchMenuItem> getAllBetween(@Param("restaurantId") int restaurantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
