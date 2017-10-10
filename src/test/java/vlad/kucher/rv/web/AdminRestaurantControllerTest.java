package vlad.kucher.rv.web;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.service.RestaurantService;
import vlad.kucher.rv.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.*;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.AdminRestaurantController.REST_URL;

public class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(KFC)));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeArray(KFC, BURGER_KING, PUZATA_HATA)));
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant created = new Restaurant("new");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Restaurant returned = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), Restaurant.class);
        created.setId(returned.getId());

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING, PUZATA_HATA, created);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(PUZATA_HATA.getId(), "updated name");
        mockMvc.perform(put(REST_URL + '/' + PUZATA_HATA.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        String actual = JsonUtil.writeArray(service.getWithoutMenu(PUZATA_HATA.getId()));
        String expected = JsonUtil.writeArray(updated);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + '/' + KFC.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(BURGER_KING, PUZATA_HATA);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetByName() throws Exception {
        mockMvc.perform(get(REST_URL + "/by?name=" + KFC.getName())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(KFC)));
    }

}