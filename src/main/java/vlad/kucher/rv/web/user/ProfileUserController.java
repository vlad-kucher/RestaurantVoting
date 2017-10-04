package vlad.kucher.rv.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.AuthorizedUser;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;

@RestController
@RequestMapping(value = ProfileUserController.REST_URL)
public class ProfileUserController {
    static final String REST_URL = "/rest/users/profile";

    @Autowired
    UserService service;

    @GetMapping
    public User get(){
        return service.get(AuthorizedUser.getId());
    }

    @DeleteMapping
    public void delete(){
        service.delete(AuthorizedUser.getId());
    }

    @PutMapping
    public void update(@RequestBody User user){
        service.update(user);
    }
}
