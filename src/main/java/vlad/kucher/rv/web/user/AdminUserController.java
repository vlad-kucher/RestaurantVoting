package vlad.kucher.rv.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL)
public class AdminUserController {
    static final String REST_URL = "/rest/admin/users";

    @Autowired
    UserService service;

    @GetMapping
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public User get(@PathVariable("id") int id){
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){
        service.delete(id);
    }

    @PostMapping
    public User create(@RequestBody User user){
        return service.create(user);
    }

    @PutMapping
    public void update(@RequestBody User user){
        service.update(user);
    }

    @GetMapping
    public User getByEmail(@RequestParam String email){
        return service.getByEmail(email);
    }
}
