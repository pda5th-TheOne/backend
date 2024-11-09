package pda5th.backend.theOne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5th.backend.theOne.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    // 특정 질문에 대한 모든 댓글을 조회하는 메서드
    List<Reply> findByQuestionId(Integer questionId);
}
