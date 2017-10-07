package vlad.kucher.rv.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.service.RestaurantService;
import vlad.kucher.rv.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL)
public class UserRestaurantController {
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private RestaurantService service;

    @GetMapping(value = "/{id}")
    public RestaurantTo get(@PathVariable("id") int id){
        return service.get(LocalDate.now(), id);
    }

    @GetMapping(value = "/{id}")
    public RestaurantTo getForDate(@RequestParam LocalDate date, @PathVariable("id") int id){
        return service.get(date, id);
    }

    @GetMapping
    public List<RestaurantTo> getAll(){
        return service.getAll(LocalDate.now());
    }

    @GetMapping
    public List<RestaurantTo> getAllForDate(@RequestParam LocalDate date){
        return service.getAll(date);
    }

    @GetMapping(value = "/{id}")
    public List<RestaurantTo> getOneWithAllMenus(@PathVariable("id") int id) {
        return service.getOneWithAllMenus(id);
    }
}
