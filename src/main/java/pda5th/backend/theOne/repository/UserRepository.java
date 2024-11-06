package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5th.backend.theOne.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 이메일을 기반으로 사용자 검색
     *
     * @param email 사용자 이메일
     * @return 사용자 엔티티(Optional)
     */
    Optional<User> findByEmail(String email);
}