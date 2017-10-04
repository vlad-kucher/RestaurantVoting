package vlad.kucher.rv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    @JsonIgnore
    private Menu menu;

    @Column(name = "price", nullable = false)
    @Positive
    // http://stackoverflow.com/a/43051227/548473
    @NotNull
    private Integer price;

    public Dish(){
    }

    public Dish(String name, Integer price) {
        super(null, name);
        this.menu = null;
        this.price = price;
    }

    public Dish(Integer id, String name, Menu menu, Integer price) {
        super(id, name);
        this.menu = menu;
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + getId() +
                ", name=" + name +
                ", price=" + price +
                ", menuId=" + menu.getId() +
                '}';
    }
}
