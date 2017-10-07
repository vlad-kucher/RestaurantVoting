package vlad.kucher.rv.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
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
    VoteService service;

    @PostMapping
    public Restaurant vote(@RequestParam int restaurantId){
        Vote vote = service.vote(restaurantId, AuthorizedUser.id());
        return vote.getMenu().getRestaurant();
    }

    @GetMapping
    public Restaurant current(){
        Vote current = service.current(AuthorizedUser.id());
        return current.getMenu().getRestaurant();
    }
}
