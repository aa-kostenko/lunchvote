package org.example.lunchvote.repository;

import org.example.lunchvote.model.LunchMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LunchMenuItemRepository extends JpaRepository<LunchMenuItem, Integer> {
}
