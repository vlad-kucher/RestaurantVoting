package vlad.kucher.rv.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import vlad.kucher.rv.util.RestaurantUtil;
import vlad.kucher.rv.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.*;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.UserRestaurantController.REST_URL;

public class UserRestaurantControllerTest  extends AbstractControllerTest {

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC.getId())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(RestaurantUtil.createTo(KFC_TODAY_MENU))));
    }

    @Test
    public void testGetForDate() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC_ID + "?date=" + KFC_OLD_MENU.getDate())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(RestaurantUtil.createTo(KFC_OLD_MENU))));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeArray(RestaurantUtil.getTo(TODAY_MENUS).toArray())));
    }

    @Test
    public void testGetAllForDate() throws Exception {
        mockMvc.perform(get(REST_URL + "?date=" + OLD_DATE)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeArray(RestaurantUtil.getTo(OLD_MENUS).toArray())));
    }
}