package com.example.PickBusBackend.service.application.user;

import com.example.PickBusBackend.Controller.user.dto.request.LocalSignUpRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalSignUpResponseDto;
import com.example.PickBusBackend.configuration.EmailEncryptor;
import com.example.PickBusBackend.repository.history.UserHistoryRepository;
import com.example.PickBusBackend.repository.history.entity.UserHistory;
import com.example.PickBusBackend.repository.history.entity.vo.Action;
import com.example.PickBusBackend.repository.user.UserRepository;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.repository.user.entity.vo.Role;
import com.example.PickBusBackend.security.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailEncryptor emailEncryptor;
    private final TokenService tokenService;
    private final UserHistoryRepository userHistoryRepository;

    @Transactional
    public LocalSignUpResponseDto signUp(LocalSignUpRequestDto request) {

        String encodePassword = passwordEncoder.encode(request.getPassword());
        String encryptedEmail = emailEncryptor.encrypt(request.getEmail());

        User user = User.create(
                request.getUsername(),
                request.getNickname(),
                encryptedEmail,
                encodePassword,
                Role.USER,
                request.getRegion()
        );

        userRepository.save(user);

        userHistoryRepository.save(
                UserHistory.create(
                        user.getId(),
                        user.getUsername(),
                        user.getNickname(),
                        Action.SIGNUP
                )
        );

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        return LocalSignUpResponseDto.from(user, accessToken, refreshToken);
    }

}
