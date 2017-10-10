package vlad.kucher.rv.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public User get(@AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("get {}", authorizedUser.getId());
        return service.get(authorizedUser.getId());
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("delete {}", authorizedUser.getId());
        service.delete(authorizedUser.getId());
    }

    @PutMapping
    public void update(@RequestBody User user, @AuthenticationPrincipal AuthorizedUser authorizedUser){
        ValidationUtil.assureIdConsistent(user, authorizedUser.getId());
        log.info("update {} with id=", user, authorizedUser.getId());
        service.update(user);
    }
}
