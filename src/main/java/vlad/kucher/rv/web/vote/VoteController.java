package vlad.kucher.rv.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Restaurant> vote(@PathVariable("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("user {} vote for restaurant {}", authorizedUser.getId(), restaurantId);
        Vote vote = service.vote(restaurantId, authorizedUser.getId());
        return new ResponseEntity<>(vote.getMenu().getRestaurant(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Restaurant> current(@AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("current vote of user {}", authorizedUser.getId());
        Vote current = service.current(authorizedUser.getId());
        return new ResponseEntity<>(current.getMenu().getRestaurant(), HttpStatus.OK);
    }
}
