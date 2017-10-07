package vlad.kucher.rv.service;

import org.junit.Assume;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.util.exception.TooLateModificationException;
import vlad.kucher.rv.web.json.JsonUtil;

import java.time.LocalTime;

import static vlad.kucher.rv.TestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void vote() throws Exception {
        Assume.assumeFalse(LocalTime.now().isAfter(VoteService.EXPIRED_TIME));
        service.vote(PUZATA_HATA_ID, USER_ID);

        String actual = JsonUtil.writeValue(service.current(USER_ID).getMenu().getRestaurant());
        String expected = JsonUtil.writeValue(PUZATA_HATA);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void voteExpired() throws Exception {
        Assume.assumeTrue(LocalTime.now().isAfter(VoteService.EXPIRED_TIME));

        thrown.expect(TooLateModificationException.class);
        service.vote(PUZATA_HATA_ID, USER_ID);
    }

    @Test
    public void current() throws Exception {
        String actual = JsonUtil.writeValue(service.current(USER_ID));
        String expected = JsonUtil.writeValue(USER_TODAY_VOTE);
        JSONAssert.assertEquals(expected, actual, false);
    }
}