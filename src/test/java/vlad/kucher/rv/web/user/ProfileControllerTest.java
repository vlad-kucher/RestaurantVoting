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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.ADMIN;
import static vlad.kucher.rv.TestData.USER;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.user.ProfileController.REST_URL;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ProfileControllerTest  extends AbstractControllerTest {

    @Autowired
    private UserService service;

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeValue(USER)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk());

        String actual = JsonUtil.writeArray(service.getAll().toArray());
        String expected = JsonUtil.writeArray(ADMIN);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User (null, "new", "new@gmail.com", "newpass", Role.ROLE_USER);

        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        String actual = JsonUtil.writeArray(service.getAll().toArray());
        String expected = JsonUtil.writeArray(updated, ADMIN);
        JSONAssert.assertEquals(expected, actual, false);
    }
}