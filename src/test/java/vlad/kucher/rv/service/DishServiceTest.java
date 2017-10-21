package vlad.kucher.rv.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.util.exception.NotFoundException;

import static vlad.kucher.rv.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void testCreate() throws Exception {
        Dish newDish = new Dish(null, "NewDish", KFC_TODAY_MENU, 7777);
        Dish created = service.create(newDish, KFC_ID);
        newDish.setId(created.getId());
        assertThat(created, samePropertyValuesAs(newDish));
    }

    @Test
    public void testGet() throws Exception {
        Dish actual = service.get(KFC_TODAY_DISH_1.getId());
        actual.setMenu(null);
        assertThat(actual, samePropertyValuesAs(KFC_TODAY_DISH_1));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(KFC_TODAY_DISH_1.getId());
        thrown.expect(NotFoundException.class);
        service.get(KFC_TODAY_DISH_1.getId());
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(-1);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(-1);
    }
}