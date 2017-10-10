package vlad.kucher.rv.web;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.service.DishService;
import vlad.kucher.rv.util.exception.NotFoundException;
import vlad.kucher.rv.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vlad.kucher.rv.TestData.ADMIN;
import static vlad.kucher.rv.TestData.KFC;
import static vlad.kucher.rv.TestData.KFC_TODAY_DISH_1;
import static vlad.kucher.rv.TestUtil.userHttpBasic;
import static vlad.kucher.rv.web.DishController.REST_URL;

public class DishControllerTest extends AbstractControllerTest{

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private DishService service;

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + KFC_TODAY_DISH_1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(KFC_TODAY_DISH_1)));
    }

    @Test
    public void testCreate() throws Exception {
        Dish created = new Dish("created dish", 777);
        ResultActions action = mockMvc.perform(post(REST_URL + "?restaurantId=" + KFC.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Dish returned = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), Dish.class);
        created.setId(returned.getId());

        String actual = JsonUtil.writeValue(created);
        String expected = JsonUtil.writeValue(service.get(created.getId()));
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + '/' + KFC_TODAY_DISH_1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        thrown.expect(NotFoundException.class);
        service.get(KFC_TODAY_DISH_1.getId());
    }
}