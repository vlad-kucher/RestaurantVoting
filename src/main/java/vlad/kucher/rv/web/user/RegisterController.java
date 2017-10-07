package vlad.kucher.rv.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;
import vlad.kucher.rv.util.ValidationUtil;

@RestController
@RequestMapping(value = RegisterController.REST_URL)
public class RegisterController {
    static final String REST_URL = "/rest/register";

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        ValidationUtil.checkNew(user);
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }
}
