package org.example.lunchvote.util;

import org.example.lunchvote.model.Vote;
import org.example.lunchvote.to.VoteTo;

public class VoteUtil {
    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId());
    }
}
