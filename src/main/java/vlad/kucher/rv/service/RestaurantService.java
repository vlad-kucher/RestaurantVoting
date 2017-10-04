package vlad.kucher.rv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.model.Restaurant;
import vlad.kucher.rv.repository.MenuRepository;
import vlad.kucher.rv.repository.RestaurantRepository;
import vlad.kucher.rv.repository.VoteRepository;
import vlad.kucher.rv.to.RestaurantTo;
import vlad.kucher.rv.util.RestaurantUtil;
import vlad.kucher.rv.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static vlad.kucher.rv.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private VoteRepository voteRepository;

    public Restaurant create(Restaurant restaurant){
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant){
        repository.save(restaurant);
    }

    @Transactional
    public RestaurantTo get(LocalDate date, int id){
        Menu menu = menuRepository.get(date, id);
        return RestaurantUtil.createTo(menu, voteRepository.countByDate(date, menu.getId()));
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Restaurant getByName(String name) {
        return repository.getByName(name);
    }

    @Transactional
    public List<RestaurantTo> getAll(LocalDate date){
        return RestaurantUtil.getTo(menuRepository.getAll(date), voteRepository.countAllByDate(date));
    }

    @Transactional
    public List<RestaurantTo> getOneWithAllMenus(int id){
        return RestaurantUtil.getTo(menuRepository.getAllForRestaurant(id), voteRepository.countAllByRestaurant(id));
    }

    public List<Restaurant> getAllWithoutMenu(){
        return repository.findAll();
    }

    public Restaurant getWithoutMenu(int id){
        return repository.findOne(id);
    }
}
