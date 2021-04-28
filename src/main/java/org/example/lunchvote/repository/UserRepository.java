package org.example.lunchvote.repository;

import org.example.lunchvote.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
