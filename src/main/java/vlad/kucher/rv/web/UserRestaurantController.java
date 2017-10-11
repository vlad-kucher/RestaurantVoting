package vlad.kucher.rv.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.service.RestaurantService;
import vlad.kucher.rv.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL)
public class UserRestaurantController {
    static final String REST_URL = "/rest/restaurants";

    private static final Logger log = getLogger(UserRestaurantController.class);

    @Autowired
    private RestaurantService service;

    @GetMapping(value = "/{id}")
    public RestaurantTo get(@PathVariable("id") int id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "date", required = false) LocalDate date){
        if (date == null) {
            log.info("get {}", id);
            return service.get(LocalDate.now(), id);
        } else {
            log.info("get {} for date {}", id, date);
            return service.get(date, id);
        }
    }

    @GetMapping
    public List<RestaurantTo> getAll(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "date", required = false) LocalDate date){
        if (date == null) {
            log.info("getAll");
            return service.getAll(LocalDate.now());
        } else {
            log.info("getAll for date {}", date);
            return service.getAll(date);
        }
    }
}
