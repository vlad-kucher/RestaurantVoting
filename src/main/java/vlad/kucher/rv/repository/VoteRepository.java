package vlad.kucher.rv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vlad.kucher.rv.model.Vote;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer>{

    @Override
    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date=:date AND v.user.id=:userId")
    Vote get(@Param("date") LocalDate date, @Param("userId") int userId);
}
