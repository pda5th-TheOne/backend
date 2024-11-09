package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5th.backend.theOne.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
