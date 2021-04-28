package org.example.lunchvote.repository;

import org.example.lunchvote.model.LunchMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LunchMenuItemRepository extends JpaRepository<LunchMenuItem, Integer> {
}
