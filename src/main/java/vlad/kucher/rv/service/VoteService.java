package vlad.kucher.rv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.repository.MenuRepository;
import vlad.kucher.rv.repository.UserRepository;
import vlad.kucher.rv.repository.VoteRepository;
import vlad.kucher.rv.util.exception.TooLateModificationException;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    public static final LocalTime EXPIRED_TIME = LocalTime.of(11, 0);

    @Autowired
    private VoteRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Vote vote(int restaurantId, int userId){
        Vote vote = repository.get(LocalDate.now(), userId);

        if (vote != null && LocalTime.now().isAfter(EXPIRED_TIME)) {
            throw new TooLateModificationException("Vote can't be changed after " + EXPIRED_TIME);
        }

        if (vote != null && vote.getMenu().getRestaurant().getId() == restaurantId) {
            return vote;
        }

        Menu menu = menuRepository.getWithoutDishes(LocalDate.now(), restaurantId);
        if (menu == null) {
            throw new IllegalArgumentException("Voting for restaurant without today's menu is forbidden");
        }

        if (vote == null) {
            return repository.save(new Vote(userRepository.getOne(userId), menu));
        } else {
            vote.setMenu(menu);
            return repository.save(vote);
        }
    }

    public Vote current(int userId) {
        return repository.get(LocalDate.now(), userId);
    }

}
