package vlad.kucher.rv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.repository.DishRepository;
import vlad.kucher.rv.repository.MenuRepository;
import vlad.kucher.rv.repository.RestaurantRepository;
import vlad.kucher.rv.util.exception.NotFoundException;

import java.time.LocalDate;

import static vlad.kucher.rv.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    @Autowired
    private DishRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Dish create(Dish dish, int restaurantId){
        Assert.notNull(dish, "dish must not be null");

        Menu menu = menuRepository.getWithoutDishes(LocalDate.now(), restaurantId);
        if (menu == null) {
            menu = menuRepository.save(new Menu(restaurantRepository.getOne(restaurantId), LocalDate.now()));
        }

        dish.setMenu(menu);

        return repository.save(dish);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
