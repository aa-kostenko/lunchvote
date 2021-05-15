package org.example.lunchvote.repository;

import org.example.lunchvote.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.lunchMenuItems WHERE r.id = :id")
    Optional<Restaurant> getWithLunchMenuItems(@Param("id") int id);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.lunchMenuItems")
    List<Restaurant> geAlltWithLunchMenuItems();

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.lunchMenuItems")
    List<Restaurant> geAllHasMenuAnyDate();

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.lunchMenuItems i WHERE i.menuDate >= :startDate and i.menuDate<= :endDate")
    List<Restaurant> geAllHasMenuBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
