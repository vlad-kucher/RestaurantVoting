package vlad.kucher.rv.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import vlad.kucher.rv.util.RestaurantUtil;
import vlad.kucher.rv.web.AbstractControllerTest;
import vlad.kucher.rv.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.*;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.restaurant.UserRestaurantController.REST_URL;

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
                .andExpect(content().json(JsonUtil.writeValue(RestaurantUtil.createTo(KFC_TODAY_MENU, 2))));
    }

    @Test
    public void testGetForDate() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC_ID + "/by?date=" + KFC_OLD_MENU.getDate())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(RestaurantUtil.createTo(KFC_OLD_MENU, 2))));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeArray(RestaurantUtil.getTo(TODAY_MENUS, COUNTS).toArray())));
    }

    @Test
    public void testGetAllForDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/by?date=" + OLD_DATE)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeArray(RestaurantUtil.getTo(OLD_MENUS, COUNTS).toArray())));
    }

    @Test
    public void testGetOneWithAllMenus() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC_ID + "/all")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeArray(RestaurantUtil.getTo(KFC_MENUS, COUNTS).toArray())));
    }

}