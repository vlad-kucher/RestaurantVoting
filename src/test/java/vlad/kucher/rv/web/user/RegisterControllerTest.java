package vlad.kucher.rv.web.user;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import vlad.kucher.rv.model.Role;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;
import vlad.kucher.rv.web.AbstractControllerTest;
import vlad.kucher.rv.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.ADMIN;
import static vlad.kucher.rv.TestData.USER;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.user.RegisterController.REST_URL;

public class RegisterControllerTest  extends AbstractControllerTest {

    @Autowired
    private UserService service;

    @Test
    public void testCreate() throws Exception {
        User created = new User(null, "name", "new@gmail.com", "password", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        String actual = JsonUtil.writeArray(service.getAll().toArray());
        String expected = JsonUtil.writeArray(USER, ADMIN, created);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testAuthorized() throws Exception {
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isForbidden());
    }
}