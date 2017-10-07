package vlad.kucher.rv.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.AuthorizedUser;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.service.VoteService;

@RestController
@RequestMapping(value = VoteController.REST_URL)
public class VoteController {
    final static String REST_URL = "/rest/users/votes";

    @Autowired
    private VoteService service;

    @PostMapping
    public ResponseEntity<Restaurant> vote(@RequestParam("restaurantId") int restaurantId){
        Vote vote = service.vote(restaurantId, AuthorizedUser.id());
        return new ResponseEntity<>(vote.getMenu().getRestaurant(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Restaurant> current(){
        Vote current = service.current(AuthorizedUser.id());
        return new ResponseEntity<>(current.getMenu().getRestaurant(), HttpStatus.OK);
    }
}
