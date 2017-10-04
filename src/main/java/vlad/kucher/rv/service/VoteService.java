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
import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Vote vote(int restaurantId, LocalDate date, int userId){
        if (date != LocalDate.now()) {
            throw new IllegalArgumentException("You can vote only for today's date");
        }

        Vote vote = repository.get(LocalDate.now(), userId);
        if (vote != null && LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            throw new TooLateModificationException("Vote can't be changed after 11:00");
        }

        Menu menu = menuRepository.getWithoutDishes(date, restaurantId);
        if (vote == null) {
            return repository.save(new Vote(userRepository.getOne(userId), menu));
        } else {
            vote.setMenu(menu);
            return repository.save(vote);
        }
    }

    public Vote get(LocalDate date, int userId) {
        return repository.get(date, userId);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }
}
