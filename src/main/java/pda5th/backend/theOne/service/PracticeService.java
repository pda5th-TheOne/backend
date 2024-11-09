package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pda5th.backend.theOne.dto.PracticeResponseDto;
import pda5th.backend.theOne.entity.Practice;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.entity.UsersPractices;
import pda5th.backend.theOne.repository.PracticeRepository;
import pda5th.backend.theOne.repository.UsersPracticesRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final UsersPracticesRepository usersPracticesRepository;

    public PracticeResponseDto getPracticeDetail(Integer id) {
        // 1. Practice 엔티티 조회
        Practice practice = practiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Practice not found with id: " + id));

        // 2. Practice에 연결된 UsersPractices를 조회 (Fetch Join 활용)
        List<UsersPractices> usersPracticesList = usersPracticesRepository.findUsersPracticesByPracticeId(practice.getId());

        // 3. Practice와 UsersPractices 정보를 기반으로 PracticeResponseDto 생성
        // UsersPractices가 비어있으면 빈 리스트를 반환
        return PracticeResponseDto.fromPracticeAndUsersPractices(practice, usersPracticesList);
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

        // answer 필드 업데이트
        practice.setAnswer(newAnswer);

        // 업데이트 된 answer 값 반환
        return practice.getAnswer();
    }

    @Transactional
    public String createSubmitUser(Integer id, User user) {
        Practice practice = practiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Practice not found with id: " + id));

        // practice와 user table을 이용해서 users_practices table 생성
        UsersPractices newUsersPractices = UsersPractices.builder()
                .practice(practice)
                .user(user)
                .createdAt(LocalDate.now())
                .build();

        // 저장
        usersPracticesRepository.save(newUsersPractices);

        // 해당 user Name 반환
        return user.getName();
    }

    public Integer deleteSumbitUser(Integer id, User user) {
        UsersPractices usersPractices = usersPracticesRepository.findByPracticeIdAndUserId(id, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("usersPractice not found with id: " + id));

        // 엔티티 삭제
        usersPracticesRepository.delete(usersPractices);

        // 삭제된 유저의 id 반환
        return user.getId();

    }

}
