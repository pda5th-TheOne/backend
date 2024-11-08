package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.entity.Practice;
import pda5th.backend.theOne.entity.UsersPractices;
import pda5th.backend.theOne.repository.PracticeRepository;
import pda5th.backend.theOne.repository.UsersPracticesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final UsersPracticesRepository usersPracticesRepository;

    public PracticeResponseDto getPracticeDetail(Integer id) {
        List<UsersPractices> usersPractices = usersPracticesRepository.findUsersPracticesByPracticeId(id);

        if (usersPractices.isEmpty()) {
            throw new RuntimeException("Practice not found with id " + id);
        }

        return PracticeResponseDto.fromUsersPractices(usersPractices);
    }

    @Transactional
    public String updateAssignment(Integer id, String newAssignment) {
        // 1. Practice 엔티티 조회
        Practice practice = practiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Practice not found with id: " + id));

        // 2. assignment 필드 업데이트
        practice.setAssignment(newAssignment);

        // 3. 변경 감지로 자동 저장 (Transactional)
        return practice.getAssignment(); // 업데이트된 assignment 반환
    }

    @Transactional
    public String updateAnswer(Integer id, String newAnswer) {
        Practice practice = practiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Practice not found with id: " + id));

        practice.setAnswer(newAnswer);
        return practice.getAnswer();
    }
}
