package vlad.kucher.rv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vlad.kucher.rv.model.Vote;
import vlad.kucher.rv.util.Count;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer>{

    @Override
    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date=:date AND v.user.id=:userId")
    Vote get(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.date=:date AND v.restaurant.id=:restaurantId")
    int countByDate(@Param("date") LocalDate date, @Param("restaurantId") int restaurantId);

    //https://stackoverflow.com/a/36329166
    @Query("SELECT new vlad.kucher.rv.util.Count(v.restaurant.id, COUNT(v)) FROM Vote v WHERE v.date=:date GROUP BY v.restaurant.id")
    List<Count> countAllByDate(@Param("date") LocalDate date);
}
