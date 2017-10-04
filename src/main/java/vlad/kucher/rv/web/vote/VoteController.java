package vlad.kucher.rv.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.AuthorizedUser;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL)
public class VoteController {
    final static String REST_URL = "/rest/users/votes";

    @Autowired
    VoteService service;

    @PostMapping
    public Vote vote(@RequestParam int restaurantId, @RequestParam LocalDate date){
        return service.vote(restaurantId, date, AuthorizedUser.getId());
    }

    @GetMapping
    public Vote get(@RequestParam LocalDate date){
        return service.get(date, AuthorizedUser.getId());
    }

    @GetMapping
    public List<Vote> getAll(){
        return service.getAll(AuthorizedUser.getId());
    }
}
