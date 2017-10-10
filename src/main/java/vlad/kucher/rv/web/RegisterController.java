package vlad.kucher.rv.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.Role;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;
import vlad.kucher.rv.util.ValidationUtil;

import java.util.EnumSet;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = RegisterController.REST_URL)
public class RegisterController {
    static final String REST_URL = "/rest/register";

    private static final Logger log = getLogger(RegisterController.class);

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        ValidationUtil.checkNew(user);

        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(EnumSet.of(Role.ROLE_USER));
        }

        log.info("create {}", user);

        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }
}
