package com.example.PickBusBackend.service.application.admin;

import com.example.PickBusBackend.Controller.user.dto.request.LocalLoginRequestDto;
import com.example.PickBusBackend.Controller.user.dto.request.LocalSignUpRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalLoginResponseDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalSignUpResponseDto;
import com.example.PickBusBackend.configuration.EmailEncryptor;
import com.example.PickBusBackend.exception.domain.UserException;
import com.example.PickBusBackend.exception.error.UserErrorCode;
import com.example.PickBusBackend.repository.history.UserHistoryRepository;
import com.example.PickBusBackend.repository.history.entity.UserHistory;
import com.example.PickBusBackend.repository.history.entity.vo.Action;
import com.example.PickBusBackend.repository.user.UserRepository;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.repository.user.entity.vo.Role;
import com.example.PickBusBackend.security.jwt.JwtProvider;
import com.example.PickBusBackend.security.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailEncryptor emailEncryptor;
    private final TokenService tokenService;
    private final UserHistoryRepository userHistoryRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LocalSignUpResponseDto signUp(LocalSignUpRequestDto request) {

        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UserException(UserErrorCode.DUPLICATE_USERNAME);
        }

        if(userRepository.existsByNickname(request.getNickname())) {
            throw new UserException(UserErrorCode.DUPLICATE_NICKNAME);
        }

        String encryptedEmail = emailEncryptor.encrypt(request.getEmail());

        if(userRepository.existsByEmail(encryptedEmail)) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());

        User admin = User.create(
                request.getUsername(),
                request.getNickname(),
                encryptedEmail,
                encodePassword,
                Role.ADMIN,
                request.getRegion()
        );

        userRepository.save(admin);

        userHistoryRepository.save(
                UserHistory.create(
                        admin.getId(),
                        admin.getUsername(),
                        admin.getNickname(),
                        Action.SIGNUP
                )
        );

        String accessToken = tokenService.generateAccessToken(admin);
        String refreshToken = tokenService.generateRefreshToken(admin);

        return LocalSignUpResponseDto.from(admin, accessToken, refreshToken);
    }


}
