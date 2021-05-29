package org.example.lunchvote.web.vote;

import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.VoteRepository;
import org.example.lunchvote.to.VoteTo;
import org.example.lunchvote.web.AbstractControllerTest;
import org.example.lunchvote.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.lunchvote.TestUtil.readFromJson;
import static org.example.lunchvote.TestUtil.userHttpBasic;
import static org.example.lunchvote.UserTestData.user1;
import static org.example.lunchvote.UserTestData.user3;
import static org.example.lunchvote.VoteTestData.*;
import static org.example.lunchvote.util.DateTimeUtil.setTimeForTest;
import static org.example.lunchvote.util.DateTimeUtil.setUseCurrentTime;
import static org.example.lunchvote.util.VoteUtil.asTo;
import static org.example.lunchvote.util.exception.ErrorType.VALIDATION_ERROR;
import static org.example.lunchvote.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_VOTE_USER_DATE;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest  extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    VoteRepository voteRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DAY1_VOTE1_ID)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(day1vote1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DAY1_VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(user1votes));
    }

    @Test
    void createWithLocationBefore11() throws Exception {
        setUseCurrentTime(false);
        setTimeForTest(TIME_FOR_VOTE);

        Vote newVote = getNew();
        VoteTo voteTo = asTo(newVote);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteTo))
                .with(userHttpBasic(user1)));

        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);

        setUseCurrentTime(true);
    }

    @Test
    void createWithLocationAfter11() throws Exception {
        setUseCurrentTime(false);
        setTimeForTest(NO_TIME_FOR_VOTE);

        Vote newVote = getNew();
        VoteTo voteTo = asTo(newVote);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteTo))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));

        setUseCurrentTime(true);
    }

    @Test
    void createWithLocationBefore11Twice() throws Exception {
        setUseCurrentTime(false);

        Vote newVote = getNew();
        VoteTo voteTo = asTo(newVote);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteTo))
                .with(userHttpBasic(user1)));

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteTo))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_VOTE_USER_DATE)));

        setUseCurrentTime(true);
    }

    @Test
    void updateBefore11() throws Exception {
        setUseCurrentTime(false);
        setTimeForTest(TIME_FOR_VOTE);

        Vote updated = getUpdated();
        VoteTo voteTo = asTo(updated);
        perform(MockMvcRequestBuilders.put(REST_URL + CURRENT_DAY_VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteTo))
                .with(userHttpBasic(user3)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(voteRepository.findById(CURRENT_DAY_VOTE1_ID).get(), updated);

        setUseCurrentTime(true);
    }

    @Test
    void updateAfter11() throws Exception {
        setUseCurrentTime(false);
        setTimeForTest(NO_TIME_FOR_VOTE);

        Vote updated = getUpdated();
        VoteTo voteTo = asTo(updated);
        perform(MockMvcRequestBuilders.put(REST_URL + CURRENT_DAY_VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteTo))
                .with(userHttpBasic(user3)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));

        setUseCurrentTime(true);
    }

}
