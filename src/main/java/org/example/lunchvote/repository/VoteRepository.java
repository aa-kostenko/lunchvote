package org.example.lunchvote.repository;

import org.example.lunchvote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
