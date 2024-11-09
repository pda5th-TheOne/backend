package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5th.backend.theOne.entity.Practice;

public interface DetailPracticeRepository extends JpaRepository<Practice, Integer> {
}
