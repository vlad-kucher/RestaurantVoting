package vlad.kucher.rv.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.service.DishService;

@RestController
@RequestMapping(value = DishController.REST_URL)
public class DishController {
    static final String REST_URL = "/rest/admin/restaurants/dishes";

    @Autowired
    DishService service;

    @PostMapping
    public Dish create(@RequestParam String name, @RequestParam int price, @RequestParam int restaurantId){
        return service.create(new Dish(name, price), restaurantId);
    }

    @PutMapping
    public void update(@RequestBody Dish dish){
        service.update(dish);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        service.delete(id);
    }

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable("id") int id){
        return service.get(id);
    }
}
