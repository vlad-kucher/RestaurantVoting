package vlad.kucher.rv;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import vlad.kucher.rv.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class TestUtil {

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static <T> void assertListEquals(List<T> actual, List<T> expected){
        assertThat(actual, hasSize(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i), samePropertyValuesAs(expected.get(i)));
        }
    }
}
