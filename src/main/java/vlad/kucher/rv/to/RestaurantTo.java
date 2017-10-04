package vlad.kucher.rv.to;

import vlad.kucher.rv.model.Dish;

import java.time.LocalDate;
import java.util.List;

public class RestaurantTo {
    private final Integer id;
    private final String name;
    private final List<Dish> dishes;
    private final LocalDate date;
    private final Integer rating;

    public RestaurantTo(Integer id, String name, List<Dish> dishes, LocalDate date, Integer rating) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
        this.date = date;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name=" + name +
                ", dishes=" + dishes +
                ", date=" + date +
                ", rating=" + rating +
                '}';
    }
}
