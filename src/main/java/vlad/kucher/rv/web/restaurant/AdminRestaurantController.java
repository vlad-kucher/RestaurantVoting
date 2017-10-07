package vlad.kucher.rv.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.service.RestaurantService;
import vlad.kucher.rv.util.ValidationUtil;

import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL)
public class AdminRestaurantController {
    static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    private RestaurantService service;

    @GetMapping(value = "{id}")
    public Restaurant get(@PathVariable("id") int id){
        return service.getWithoutMenu(id);
    }

    @GetMapping
    public List<Restaurant> getAll(){
        return service.getAllWithoutMenu();
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant){
        return service.create(restaurant);
    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id){
        ValidationUtil.assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @GetMapping
    public Restaurant getByName(@RequestParam String name){
        return service.getByName(name);
    }
}
