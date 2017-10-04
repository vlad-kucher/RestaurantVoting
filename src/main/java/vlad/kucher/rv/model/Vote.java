package vlad.kucher.rv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    @JsonIgnore
    private Menu menu;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Vote(){
    }

    public Vote(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
        this.date = LocalDate.now();
    }

    public Vote(Integer id, User user, Menu menu, LocalDate date) {
        super(id);
        this.user = user;
        this.menu = menu;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "userId=" + user.getId() +
                ", menuId=" + menu.getId() +
                ", date=" + date +
                '}';
    }
}
