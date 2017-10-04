package vlad.kucher.rv.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vlad.kucher.rv.web.json.JsonUtil;

import java.time.LocalDate;

import static vlad.kucher.rv.TestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void vote() throws Exception {
        //TODO
    }

    @Test
    public void get() throws Exception {
        String actual = JsonUtil.writeValue(service.get(LocalDate.now(), USER_ID));
        String expected = JsonUtil.writeValue(USER_TODAY_VOTE);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void getAll() throws Exception {
        String actual = JsonUtil.writeArray(service.getAll(USER_ID).toArray());
        String expected = JsonUtil.writeArray(USER_TODAY_VOTE, USER_OLD_VOTE);
        JSONAssert.assertEquals(expected, actual, false);
    }

}