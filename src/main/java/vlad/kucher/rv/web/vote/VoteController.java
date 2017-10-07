package vlad.kucher.rv.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.AuthorizedUser;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.service.VoteService;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = VoteController.REST_URL)
public class VoteController {
    final static String REST_URL = "/rest/vote";

    private static final Logger log = getLogger(VoteController.class);

    @Autowired
    private VoteService service;

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<Restaurant> vote(@PathVariable("restaurantId") int restaurantId){
        log.info("user {} vote for restaurant {}", AuthorizedUser.id(), restaurantId);
        Vote vote = service.vote(restaurantId, AuthorizedUser.id());
        return new ResponseEntity<>(vote.getMenu().getRestaurant(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Restaurant> current(){
        log.info("current vote of user {}", AuthorizedUser.id());
        Vote current = service.current(AuthorizedUser.id());
        return new ResponseEntity<>(current.getMenu().getRestaurant(), HttpStatus.OK);
    }
}
