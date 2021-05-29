package org.example.lunchvote;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.to.VoteResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.lunchvote.RestaurantTestData.*;
import static org.example.lunchvote.UserTestData.*;

public class VoteTestData {
    public static org.example.lunchvote.TestMatcher<Vote> VOTE_MATCHER =
            TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant", "dateTime");

    public static org.example.lunchvote.TestMatcher<VoteResult> VOTE_RESULT_MATCHER =
            TestMatcher.usingIgnoringFieldsComparator(VoteResult.class);

    public static final int NOT_FOUND = 0;

    public static final LocalTime TIME_FOR_VOTE = LocalTime.of(1,2,3);
    public static final LocalTime NO_TIME_FOR_VOTE = LocalTime.of(11,22,33);

    public static final int DAY1_VOTE1_ID = 1;
    public static final int DAY1_VOTE2_ID = DAY1_VOTE1_ID + 1;
    public static final int DAY1_VOTE3_ID = DAY1_VOTE2_ID + 1;

    public static final int DAY2_VOTE1_ID = DAY1_VOTE1_ID + 3;
    public static final int DAY2_VOTE2_ID = DAY2_VOTE1_ID + 1;
    public static final int DAY2_VOTE3_ID = DAY2_VOTE2_ID + 1;

    public static final int CURRENT_DAY_VOTE1_ID = DAY2_VOTE3_ID + 1;

    public static final Vote day1vote1 = new Vote(
            DAY1_VOTE1_ID,
            LocalDateTime.of(2021,1,30, 9,20,55),
            user1,
            restauraunt1
            );

    public static final Vote day1vote2 = new Vote(
            DAY1_VOTE2_ID,
            LocalDateTime.of(2021,1,30, 10,45,15),
            user2,
            restauraunt1
    );

    public static final Vote day1vote3 = new Vote(
            DAY1_VOTE3_ID,
            LocalDateTime.of(2021,1,30, 8,5,44),
            user3,
            restauraunt2
    );

    public static final Vote day2vote1 = new Vote(
            DAY2_VOTE1_ID,
            LocalDateTime.of(2021,1,31, 8,22,33),
            user1,
            restauraunt1
    );

    public static final Vote day2vote2 = new Vote(
            DAY2_VOTE2_ID,
            LocalDateTime.of(2021,1,31, 9,57,2),
            user2,
            restauraunt2
    );

    public static final Vote day2vote3 = new Vote(
            DAY2_VOTE3_ID,
            LocalDateTime.of(2021,1,31, 8,7,1),
            user3,
            restauraunt2
    );

    public static final Vote currentDayVote1 = new Vote(
            CURRENT_DAY_VOTE1_ID,
            LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0,0)),
            user3,
            restauraunt4
    );

    public static final List<Vote> day1votes = List.of(day1vote1, day1vote2, day1vote3);
    public static final List<Vote> day2votes = List.of(day2vote1, day2vote2, day2vote3);
    public static final List<Vote> currDayVotes = List.of(currentDayVote1);
    public static final List<Vote> user1votes = List.of(day1vote1, day2vote1);

    public static final List<Vote> votes =
            Stream.of(day1votes, day2votes, currDayVotes)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

    public static final Vote getNew(){
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 10,10));
        return new Vote(null, dateTime, user1, restauraunt1);
    }

    public static final Vote getUpdated(){
        return new Vote(
                CURRENT_DAY_VOTE1_ID,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10,0,0)),
                user3,
                restauraunt1
        );
    }

    public static final VoteResult todayVoteResult = new VoteResult(restauraunt4, LocalDate.now(), 1L);
    public static final List<VoteResult> todayVoteResultList = List.of(todayVoteResult);

}
