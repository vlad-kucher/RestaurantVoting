package vlad.kucher.rv.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;

@RestController
@RequestMapping(value = RegisterController.REST_URL)
public class RegisterController {
    static final String REST_URL = "/rest/register";

    @Autowired
    UserService service;

    @PostMapping
    public User create(@RequestBody User user){
        return service.create(user);
    }
}
