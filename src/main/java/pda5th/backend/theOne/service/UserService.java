package pda5th.backend.theOne.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pda5th.backend.theOne.dto.SignUpRequest;
import pda5th.backend.theOne.entity.Role;
import pda5th.backend.theOne.entity.User;
import pda5th.backend.theOne.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request){
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // 비밀번호 암호화
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }
}
