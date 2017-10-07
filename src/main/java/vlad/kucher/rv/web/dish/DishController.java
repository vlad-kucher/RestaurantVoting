package vlad.kucher.rv.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.service.DishService;
import vlad.kucher.rv.util.ValidationUtil;

@RestController
@RequestMapping(value = DishController.REST_URL)
public class DishController {
    static final String REST_URL = "/rest/admin/restaurants/dishes";

    @Autowired
    private DishService service;

    @PostMapping
    public ResponseEntity<Dish> create(@RequestParam String name, @RequestParam int price, @RequestParam int restaurantId){
        return new ResponseEntity<>(service.create(new Dish(name, price), restaurantId), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody Dish dish, @PathVariable("id") int id){
        ValidationUtil.assureIdConsistent(dish, id);
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
