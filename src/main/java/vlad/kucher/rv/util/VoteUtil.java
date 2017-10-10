package vlad.kucher.rv.util;

import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.util.exception.TooLateModificationException;

import java.time.LocalTime;

public class VoteUtil {
    public static final LocalTime EXPIRED_TIME = LocalTime.of(11, 0);

    private VoteUtil() {
    }

    public static void checkExpired(Vote vote) {
        if (vote != null && LocalTime.now().isAfter(VoteUtil.EXPIRED_TIME)) {
            throw new TooLateModificationException("Vote can't be changed after " + VoteUtil.EXPIRED_TIME);
        }
    }
}
