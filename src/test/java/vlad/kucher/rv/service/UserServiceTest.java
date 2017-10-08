package vlad.kucher.rv.service;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Role;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.util.exception.NotFoundException;
import vlad.kucher.rv.web.json.JsonUtil;

import javax.validation.ConstraintViolationException;

import static vlad.kucher.rv.TestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void testCreate() throws Exception {
        User newUser = new User(null, "New name", "new@gmail.com", "New pass", Role.ROLE_USER);
        User created = service.create(newUser);
        newUser.setId(created.getId());

        String actual = JsonUtil.writeArray(service.getAll().toArray());
        String expected = JsonUtil.writeArray(USER, ADMIN, newUser);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID);

        String actual = JsonUtil.writeArray(service.getAll().toArray());
        String expected = JsonUtil.writeArray(ADMIN);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGet() throws Exception {
        String actual = JsonUtil.writeValue(service.get(USER_ID));
        String expected = JsonUtil.writeValue(USER);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetByEmail() throws Exception {
        String actual = JsonUtil.writeValue(service.getByEmail(ADMIN.getEmail()));
        String expected = JsonUtil.writeValue(ADMIN);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetAll() throws Exception {
        String actual = JsonUtil.writeArray(service.getAll().toArray());
        String expected = JsonUtil.writeArray(USER, ADMIN);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("NewName");
        service.update(updated);

        String actual = JsonUtil.writeValue(service.get(USER_ID));
        String expected = JsonUtil.writeValue(updated);
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(-1);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(-1);
    }

    @Test
    public void testDuplicateMailCreate() throws Exception {
        thrown.expect(ConstraintViolationException.class);
        service.create(new User(null, "duplicate", USER.getEmail(), "pass", Role.ROLE_USER));
    }
}