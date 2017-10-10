package vlad.kucher.rv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.repository.MenuRepository;
import vlad.kucher.rv.repository.UserRepository;
import vlad.kucher.rv.repository.VoteRepository;
import vlad.kucher.rv.util.VoteUtil;

import java.time.LocalDate;

@Service
public class VoteService {

    @Autowired
    private VoteRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Vote vote(int restaurantId, int userId){
        Vote vote = repository.get(LocalDate.now(), userId);

        VoteUtil.checkExpired(vote);

        if (vote != null && vote.getRestaurant().getId() == restaurantId) {
            return vote;
        }

        Menu menu = menuRepository.getWithoutDishes(LocalDate.now(), restaurantId);
        Assert.notNull(menu, "Voting for restaurant without today's menu is forbidden");

        if (vote == null) {
            return repository.save(new Vote(userRepository.getOne(userId), menu.getRestaurant()));
        } else {
            vote.setRestaurant(menu.getRestaurant());
            return repository.save(vote);
        }
    }

    public Vote current(int userId) {
        return repository.get(LocalDate.now(), userId);
    }

}
