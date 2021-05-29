package org.example.lunchvote.repository;

import org.example.lunchvote.model.Vote;
import org.example.lunchvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.lunchvote.LunchMenuItemTestData.NOT_FOUND;
import static org.example.lunchvote.RestaurantTestData.restauraunt1;
import static org.example.lunchvote.UserTestData.USER1_ID;
import static org.example.lunchvote.UserTestData.user1;
import static org.example.lunchvote.VoteTestData.*;
import static org.example.lunchvote.util.DateTimeUtil.atStartOfDayOrMin;
import static org.example.lunchvote.util.DateTimeUtil.atStartOfNextDayOrMax;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoteRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    VoteRepository repository;

    @Test
    void create() {
        Vote created = repository.save(getNew());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.findById(newId).get(), newVote);
    }

    @Test
    void createDoubleDateUser() {
        assertThrows(DataAccessException.class, () ->
                repository.save(new Vote(
                        null,
                        LocalDateTime.of(2021,1,30, 10,11,12),
                        user1,
                        restauraunt1)));
    }

    @Test
    void get() {
        Vote vote = repository.getForUser(DAY1_VOTE1_ID, USER1_ID).get();
        VOTE_MATCHER.assertMatch(vote, day1vote1);
    }

    @Test
    void getAllByUserId(){
        List<Vote> voteList = repository.getAllForUser(USER1_ID);
        VOTE_MATCHER.assertMatch(voteList, user1votes);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> repository
                        .findById(NOT_FOUND)
                        .orElseThrow(() -> new NotFoundException("not found Vote with id " + NOT_FOUND)));
    }

    @Test
    void getAllBetweenOneDayByUserId(){
        LocalDate date = LocalDate.of(2021, 1, 30);
        List<Vote> voteList = repository.getAllBetweenForUser(
                atStartOfDayOrMin(date),
                atStartOfNextDayOrMax(date),
                USER1_ID);
        VOTE_MATCHER.assertMatch(voteList, day1vote1);
    }

    @Test
    void getAllBetweenTwoDaysByUserId(){
        LocalDate startDate = LocalDate.of(2021, 1, 30);
        LocalDate endDate = LocalDate.of(2021, 1, 31);
        List<Vote> voteList = repository.getAllBetweenForUser(
                atStartOfDayOrMin(startDate),
                atStartOfNextDayOrMax(endDate),
                USER1_ID);
        List<Vote> user1_votes = user1votes;
        VOTE_MATCHER.assertMatch(voteList, user1votes);
    }

    @Test
    void getAllInNoWorkDayByUserId(){
        LocalDate date = LocalDate.of(1900, 1, 30);
        List<Vote> voteList = repository.getAllBetweenForUser(
                atStartOfDayOrMin(date),
                atStartOfNextDayOrMax(date),
                USER1_ID);
        VOTE_MATCHER.assertMatch(voteList, List.of());
    }
}
