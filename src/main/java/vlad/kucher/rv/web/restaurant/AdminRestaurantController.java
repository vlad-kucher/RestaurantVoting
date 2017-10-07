package vlad.kucher.rv.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.service.RestaurantService;
import vlad.kucher.rv.util.ValidationUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL)
public class AdminRestaurantController {
    static final String REST_URL = "/rest/admin/restaurants";

    private static final Logger log = getLogger(AdminRestaurantController.class);

    @Autowired
    private RestaurantService service;

    @GetMapping(value = "{id}")
    public Restaurant get(@PathVariable("id") int id){
        log.info("get {}", id);
        return service.getWithoutMenu(id);
    }

    @GetMapping
    public List<Restaurant> getAll(){
        log.info("getAll");
        return service.getAllWithoutMenu();
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant){
        ValidationUtil.checkNew(restaurant);
        log.info("create {}", restaurant);
        return new ResponseEntity<>(service.create(restaurant), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id){
        ValidationUtil.assureIdConsistent(restaurant, id);
        log.info("update {} with id={}", restaurant, id);
        service.update(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @GetMapping(value = "/by")
    public Restaurant getByName(@RequestParam("name") String name){
        log.info("getByName {}", name);
        return service.getByName(name);
    }
}
