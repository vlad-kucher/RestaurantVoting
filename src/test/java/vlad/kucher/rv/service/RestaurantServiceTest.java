package vlad.kucher.rv.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.util.RestaurantUtil;
import vlad.kucher.rv.web.json.JsonUtil;

import java.time.LocalDate;

import static vlad.kucher.rv.TestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void create() throws Exception {
        Restaurant newRestaurant = new Restaurant("New restaurant");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING, PUZATA_HATA, newRestaurant);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = new Restaurant(KFC.getId(), "New name");
        service.update(updated);

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(updated, BURGER_KING, PUZATA_HATA);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void get() throws Exception {
        String actual = JsonUtil.writeValue(service.get(LocalDate.now(), 0));
        String expected = JsonUtil.writeValue(RestaurantUtil.createTo(KFC_TODAY_MENU, 2));
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void delete() throws Exception {
        service.delete(PUZATA_HATA_ID);

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void getByName() throws Exception {
        String actual = JsonUtil.writeValue(service.getByName(KFC.getName()));
        String expected = JsonUtil.writeValue(KFC);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void getAll() throws Exception {
        String actual = JsonUtil.writeArray(service.getAll(LocalDate.now()).toArray());
        String expected = JsonUtil.writeArray(RestaurantUtil.getTo(TODAY_MENUS, COUNTS).toArray());
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void getOneWithAllMenus() throws Exception {
        String actual = JsonUtil.writeArray(service.getOneWithAllMenus(0).toArray());
        String expected = JsonUtil.writeArray(RestaurantUtil.getTo(KFC_MENUS, COUNTS).toArray());
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void getAllWithoutMenu() throws Exception {
        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING, PUZATA_HATA);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void getWithoutMenu() throws Exception {
        String actual = JsonUtil.writeValue(service.getWithoutMenu(KFC_ID));
        String expected = JsonUtil.writeValue(KFC);
        JSONAssert.assertEquals(actual, expected, false);
    }

}