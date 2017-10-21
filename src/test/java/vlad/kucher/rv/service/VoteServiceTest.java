package vlad.kucher.rv.service;

import org.junit.Assume;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.util.VoteUtil;
import vlad.kucher.rv.util.exception.TooLateModificationException;

import java.time.LocalTime;

import static vlad.kucher.rv.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void testVote() throws Exception {
        Assume.assumeFalse(LocalTime.now().isAfter(VoteUtil.EXPIRED_TIME));
        service.vote(PUZATA_HATA_ID, USER_ID);

        Vote actual = service.current(USER_ID);
        actual.setUser(USER);
        assertThat(actual, samePropertyValuesAs(USER_CHANGED_VOTE));
    }

    @Test
    public void testVoteExpired() throws Exception {
        Assume.assumeTrue(LocalTime.now().isAfter(VoteUtil.EXPIRED_TIME));

        thrown.expect(TooLateModificationException.class);
        service.vote(PUZATA_HATA_ID, USER_ID);
    }

    @Test
    public void testCurrent() throws Exception {
        Vote actual = service.current(USER_ID);
        actual.setUser(USER);
        assertThat(actual, samePropertyValuesAs(USER_TODAY_VOTE));
    }
}