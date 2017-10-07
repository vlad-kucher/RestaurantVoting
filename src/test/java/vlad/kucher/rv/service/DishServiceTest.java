package vlad.kucher.rv.service;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.util.exception.NotFoundException;
import vlad.kucher.rv.web.json.JsonUtil;

import static vlad.kucher.rv.TestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void create() throws Exception {
        Dish newDish = new Dish(null, "NewDish", KFC_TODAY_MENU, 7777);
        Dish created = service.create(newDish, KFC_ID);
        newDish.setId(created.getId());

        String actual = JsonUtil.writeValue(created);
        String expected = JsonUtil.writeValue(newDish);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void update() throws Exception {
        Dish updated = new Dish(KFC_OLD_DISH_1.getId(), KFC_OLD_DISH_1.getName(), KFC_OLD_MENU, KFC_OLD_DISH_1.getPrice());
        updated.setName("NewName");
        service.update(updated);

        String actual = JsonUtil.writeValue(service.get(KFC_OLD_DISH_1.getId()));
        String expected = JsonUtil.writeValue(updated);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void get() throws Exception {
        String actual = JsonUtil.writeValue(service.get(KFC_TODAY_DISH_1.getId()));
        String expected = JsonUtil.writeValue(KFC_TODAY_DISH_1);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void delete() throws Exception {
        service.delete(KFC_TODAY_DISH_1.getId());

        thrown.expect(NotFoundException.class);
        service.get(KFC_TODAY_DISH_1.getId());
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(-1);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(-1);
    }
}