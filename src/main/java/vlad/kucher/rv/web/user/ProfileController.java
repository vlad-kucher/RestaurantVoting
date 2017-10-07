package vlad.kucher.rv.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.AuthorizedUser;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;
import vlad.kucher.rv.util.ValidationUtil;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = ProfileController.REST_URL)
public class ProfileController {
    static final String REST_URL = "/rest/profile";

    private static final Logger log = getLogger(ProfileController.class);

    @Autowired
    private UserService service;

    @GetMapping
    public User get(){
        log.info("get {}", AuthorizedUser.id());
        return service.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete(){
        log.info("delete {}", AuthorizedUser.id());
        service.delete(AuthorizedUser.id());
    }

    @PutMapping
    public void update(@RequestBody User user){
        ValidationUtil.assureIdConsistent(user, AuthorizedUser.id());
        log.info("update {} with id=", user, AuthorizedUser.id());
        service.update(user);
    }
}
