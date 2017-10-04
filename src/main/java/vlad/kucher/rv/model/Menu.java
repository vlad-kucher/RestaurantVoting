package vlad.kucher.rv.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    private List<Dish> dishes;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Menu(){
    }

    public Menu(Restaurant restaurant, LocalDate date) {
        this.restaurant = restaurant;
        this.dishes = null;
        this.date = date;
    }

    public Menu(Integer id, Restaurant restaurant, List<Dish> dishes, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + getId() +
                ", date=" + date +
                ", restaurantId=" + restaurant.getId() +
                '}';
    }
}
