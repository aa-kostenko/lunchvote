package org.example.lunchvote;

import org.example.lunchvote.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.lunchvote.RestaurantTestData.restauraunt1;
import static org.example.lunchvote.RestaurantTestData.restauraunt2;
import static org.example.lunchvote.UserTestData.*;

public class VoteTestData {
    public static org.example.lunchvote.TestMatcher<Vote> VOTE_MATCHER =
            TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final int DAY1_VOTE1_ID = 1;
    public static final int DAY1_VOTE2_ID = DAY1_VOTE1_ID + 1;
    public static final int DAY1_VOTE3_ID = DAY1_VOTE2_ID + 1;

    public static final int DAY2_VOTE1_ID = DAY1_VOTE1_ID + 3;
    public static final int DAY2_VOTE2_ID = DAY2_VOTE1_ID + 1;
    public static final int DAY2_VOTE3_ID = DAY2_VOTE2_ID + 1;

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

    public static final List<Vote> day1votes = List.of(day1vote1, day1vote2, day1vote3);
    public static final List<Vote> day2votes = List.of(day2vote1, day2vote2, day2vote3);
    public static final List<Vote> user1votes = List.of(day1vote1, day2vote1);
    public static final List<Vote> votes = Stream.concat(day1votes.stream(), day2votes.stream()).collect(Collectors.toList());


    public static final Vote getNew(){
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 10,10));
        return new Vote(null, dateTime, user1, restauraunt1);
    }
}
