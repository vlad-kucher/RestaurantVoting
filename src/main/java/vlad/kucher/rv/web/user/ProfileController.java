package vlad.kucher.rv.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.AuthorizedUser;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;
import vlad.kucher.rv.util.ValidationUtil;

@RestController
@RequestMapping(value = ProfileController.REST_URL)
public class ProfileController {
    static final String REST_URL = "/rest/profile";

    @Autowired
    private UserService service;

    @GetMapping
    public User get(){
        return service.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete(){
        service.delete(AuthorizedUser.id());
    }

    @PutMapping
    public void update(@RequestBody User user){
        ValidationUtil.assureIdConsistent(user, AuthorizedUser.id());
        service.update(user);
    }
}
