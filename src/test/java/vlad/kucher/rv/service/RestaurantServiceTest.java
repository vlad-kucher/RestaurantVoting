package vlad.kucher.rv.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.to.RestaurantTo;
import vlad.kucher.rv.util.RestaurantUtil;
import vlad.kucher.rv.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static vlad.kucher.rv.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static vlad.kucher.rv.TestUtil.assertListEquals;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void testCreate() throws Exception {
        Restaurant newRestaurant = new Restaurant("New restaurant");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertListEquals(service.getAllWithoutMenu(), Arrays.asList(KFC, BURGER_KING, PUZATA_HATA, newRestaurant));
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(KFC.getId(), "New name");
        service.update(updated);
        assertListEquals(service.getAllWithoutMenu(), Arrays.asList(updated, BURGER_KING, PUZATA_HATA));
    }

    @Test
    public void testGet() throws Exception {
        RestaurantTo actual = service.get(LocalDate.now(), 0);
        RestaurantTo expected = RestaurantUtil.createTo(KFC_TODAY_MENU);
        actual.getDishes().forEach(dish -> dish.setMenu(null));

        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDate(), expected.getDate());
        assertListEquals(actual.getDishes(), expected.getDishes());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(PUZATA_HATA_ID);
        assertListEquals(service.getAllWithoutMenu(), Arrays.asList(KFC, BURGER_KING));
    }

    @Test
    public void testGetByName() throws Exception {
        assertThat(service.getByName(KFC.getName()), samePropertyValuesAs(KFC));
    }

    @Test
    public void testGetAll() throws Exception {
        List<RestaurantTo> actual = service.getAll(LocalDate.now());
        List<RestaurantTo> expected = RestaurantUtil.getTo(TODAY_MENUS);
        actual.forEach(rt -> rt.getDishes().forEach(dish -> dish.setMenu(null)));
        assertThat(actual, hasSize(expected.size()));

        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(actual.get(i).getId(), expected.get(i).getId());
            Assert.assertEquals(actual.get(i).getName(), expected.get(i).getName());
            Assert.assertEquals(actual.get(i).getDate(), expected.get(i).getDate());
            assertListEquals(actual.get(i).getDishes(), expected.get(i).getDishes());
        }
    }

    @Test
    public void testGetAllWithoutMenu() throws Exception {
        assertListEquals(service.getAllWithoutMenu(), Arrays.asList(KFC, BURGER_KING, PUZATA_HATA));
    }

    @Test
    public void testGetWithoutMenu() throws Exception {
        assertThat(service.getWithoutMenu(KFC_ID), samePropertyValuesAs(KFC));
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