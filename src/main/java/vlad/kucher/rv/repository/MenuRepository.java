package vlad.kucher.rv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vlad.kucher.rv.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Override
    @Transactional
    Menu save(Menu menu);

    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant JOIN FETCH m.dishes WHERE m.date=:date AND m.restaurant.id=:restaurantId")
    Menu get(@Param("date") LocalDate date, @Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant WHERE m.date=:date AND m.restaurant.id=:restaurantId")
    Menu getWithoutDishes(@Param("date") LocalDate date, @Param("restaurantId") int restaurantId);

    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant JOIN FETCH m.dishes WHERE m.date=:date")
    List<Menu> getAll(@Param("date") LocalDate date);
}
