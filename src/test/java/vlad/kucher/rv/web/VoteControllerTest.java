package vlad.kucher.rv.web;

import org.junit.Assume;
import org.junit.Test;
import org.springframework.http.MediaType;
import vlad.kucher.rv.util.VoteUtil;
import vlad.kucher.rv.web.json.JsonUtil;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.BURGER_KING;
import static vlad.kucher.rv.TestData.USER;
import static vlad.kucher.rv.TestData.USER_TODAY_VOTE;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.VoteController.REST_URL;

public class VoteControllerTest  extends AbstractControllerTest {

    @Test
    public void testVoteUnauth() throws Exception {
        mockMvc.perform(post(REST_URL + '/' + BURGER_KING.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testVote() throws Exception {
        Assume.assumeFalse(LocalTime.now().isAfter(VoteUtil.EXPIRED_TIME));
        mockMvc.perform(post(REST_URL + '/' + BURGER_KING.getId())
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(BURGER_KING)));
    }

    @Test
    public void testVoteExpired() throws Exception {
        Assume.assumeTrue(LocalTime.now().isAfter(VoteUtil.EXPIRED_TIME));
        mockMvc.perform(post(REST_URL + '/' + BURGER_KING.getId())
                .with(userHttpBasic(USER)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCurrent() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(USER_TODAY_VOTE.getRestaurant())));
    }
}