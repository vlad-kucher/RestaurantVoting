package vlad.kucher.rv.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.kucher.rv.model.Role;
import vlad.kucher.rv.model.User;
import vlad.kucher.rv.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import java.util.Arrays;
import java.util.Collections;

import static vlad.kucher.rv.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static vlad.kucher.rv.TestUtil.assertListEquals;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void testCreate() throws Exception {
        User newUser = new User(null, "New name", "new@gmail.com", "New pass", Role.ROLE_USER);
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertListEquals(service.getAll(), Arrays.asList(USER, ADMIN, created));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        assertListEquals(service.getAll(), Collections.singletonList(ADMIN));
    }

    @Test
    public void testGet() throws Exception {
        User actual = service.get(USER_ID);
        assertThat(actual, samePropertyValuesAs(USER));
    }

    @Test
    public void testGetByEmail() throws Exception {
        User actual = service.getByEmail(ADMIN.getEmail());
        assertThat(actual, samePropertyValuesAs(ADMIN));
    }

    @Test
    public void testGetAll() throws Exception {
        assertListEquals(service.getAll(), Arrays.asList(USER, ADMIN));
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("NewName");
        service.update(updated);

        User actual = service.get(USER_ID);
        assertThat(actual, samePropertyValuesAs(updated));
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