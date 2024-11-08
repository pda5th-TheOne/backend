package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pda5th.backend.theOne.entity.UsersPractices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersPracticesRepository extends JpaRepository<UsersPractices, Integer> {

    @Query("SELECT up FROM UsersPractices up " +
            "JOIN FETCH up.practice p " +
            "JOIN FETCH up.user u " +
            "WHERE p.id = :practiceId")
    List<UsersPractices> findUsersPracticesByPracticeId(@Param("practiceId") Integer practiceId);
}