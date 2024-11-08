package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.dto.TILCreateRequest;
import pda5th.backend.theOne.dto.TILResponse;
import pda5th.backend.theOne.dto.TILUpdateRequest;
import pda5th.backend.theOne.entity.TIL;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.repository.TILRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TILService {
    private final TILRepository tilRepository;

    // Create
    public TILResponse createTIL(User user, TILCreateRequest request) {
        TIL til = TIL.builder()
                .title(request.title())
                .link(request.link())
                .user(user)
                .build();

        til = tilRepository.save(til);

        // TIL이 생성된 후 TILResponse를 반환
        return new TILResponse(til.getId(), til.getTitle(), til.getLink(), til.getUser().getId());
    }

    // Read: Get TIL by ID
    public TILResponse getTILById(Integer id) {
        Optional<TIL> optionalTIL = tilRepository.findById(id);
        return optionalTIL.map(til -> new TILResponse(til.getId(), til.getTitle(), til.getLink(), til.getUser().getId()))
                .orElse(null);  // TIL이 없으면 null 반환
    }

    // Update: Update TIL by ID
    public TILResponse updateTIL(Integer id, TILUpdateRequest request) {
        Optional<TIL> optionalTIL = tilRepository.findById(id);
        if (optionalTIL.isPresent()) {
            TIL til = optionalTIL.get();
            til.setTitle(request.title());
            til.setLink(request.link());
            // Optional: If you want to update the user or other fields, you can do so here
            til = tilRepository.save(til);
            return new TILResponse(til.getId(), til.getTitle(), til.getLink(), til.getUser().getId());
        }
        return null;  // TIL not found
    }

    // Delete: Delete TIL by ID
    public boolean deleteTIL(Integer id) {
        Optional<TIL> optionalTIL = tilRepository.findById(id);
        if (optionalTIL.isPresent()) {
            tilRepository.deleteById(id);
            return true;
        }
        return false;  // TIL not found
    }
}
