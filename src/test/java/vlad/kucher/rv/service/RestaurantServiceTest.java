package vlad.kucher.rv.service;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.util.RestaurantUtil;
import vlad.kucher.rv.util.exception.NotFoundException;
import vlad.kucher.rv.web.json.JsonUtil;

import java.time.LocalDate;

import static vlad.kucher.rv.TestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void testCreate() throws Exception {
        Restaurant newRestaurant = new Restaurant("New restaurant");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING, PUZATA_HATA, newRestaurant);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(KFC.getId(), "New name");
        service.update(updated);

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(updated, BURGER_KING, PUZATA_HATA);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGet() throws Exception {
        String actual = JsonUtil.writeValue(service.get(LocalDate.now(), 0));
        String expected = JsonUtil.writeValue(RestaurantUtil.createTo(KFC_TODAY_MENU));
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(PUZATA_HATA_ID);

        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetByName() throws Exception {
        String actual = JsonUtil.writeValue(service.getByName(KFC.getName()));
        String expected = JsonUtil.writeValue(KFC);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetAll() throws Exception {
        String actual = JsonUtil.writeArray(service.getAll(LocalDate.now()).toArray());
        String expected = JsonUtil.writeArray(RestaurantUtil.getTo(TODAY_MENUS).toArray());
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetAllWithoutMenu() throws Exception {
        String actual = JsonUtil.writeArray(service.getAllWithoutMenu().toArray());
        String expected = JsonUtil.writeArray(KFC, BURGER_KING, PUZATA_HATA);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetWithoutMenu() throws Exception {
        String actual = JsonUtil.writeValue(service.getWithoutMenu(KFC_ID));
        String expected = JsonUtil.writeValue(KFC);
        JSONAssert.assertEquals(actual, expected, false);
    }

    @Test
    public void testGetNotFoundWithoutMenu() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithoutMenu(-1);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        service.get(LocalDate.now(),-1);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(-1);
    }
}