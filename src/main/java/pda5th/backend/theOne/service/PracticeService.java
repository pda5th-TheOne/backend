package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.dto.PracticeResponseDto;
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
}
