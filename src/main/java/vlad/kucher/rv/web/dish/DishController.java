package vlad.kucher.rv.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.Dish;
import vlad.kucher.rv.service.DishService;
import vlad.kucher.rv.util.ValidationUtil;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = DishController.REST_URL)
public class DishController {
    static final String REST_URL = "/rest/admin/dishes";

    private static final Logger log = getLogger(DishController.class);

    @Autowired
    private DishService service;

    @PostMapping
    public ResponseEntity<Dish> create(@RequestBody Dish dish, @RequestParam("restaurantId") int restaurantId){
        ValidationUtil.checkNew(dish);
        log.info("create {} for restaurant {}", dish, restaurantId);
        return new ResponseEntity<>(service.create(dish, restaurantId), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        log.info("delete {}", id);
        service.delete(id);
    }

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable("id") int id){
        log.info("get {}", id);
        return service.get(id);
    }
}
