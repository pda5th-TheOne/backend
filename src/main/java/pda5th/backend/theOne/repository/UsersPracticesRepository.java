package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pda5th.backend.theOne.entity.UsersPractices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersPracticesRepository extends JpaRepository<UsersPractices, Integer> {

    @Query("SELECT up FROM UsersPractices up " +
            "JOIN FETCH up.practice p " +
            "JOIN FETCH up.user u " +
            "WHERE p.id = :practiceId")
    List<UsersPractices> findUsersPracticesByPracticeId(@Param("practiceId") Integer practiceId);

    // 제출완료 취소 버튼 누르는 경우, practiceId, userId 둘 다 같은 값을 찾아줌
    @Query("SELECT up FROM UsersPractices up " +
            "JOIN FETCH up.practice p " +
            "JOIN FETCH up.user u " +
            "WHERE p.id = :practiceId AND u.id = :userId")
    Optional<UsersPractices> findByPracticeIdAndUserId(@Param("practiceId") Integer practiceId, @Param("userId") Integer userId);
}

