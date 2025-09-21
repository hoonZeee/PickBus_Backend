package com.example.PickBusBackend.service.application.user;

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
public class UserService {

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

    @Transactional
    public LocalLoginResponseDto login(LocalLoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new UserException(UserErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }

        userHistoryRepository.save(
                UserHistory.create(
                        user.getId(),
                        user.getUsername(),
                        user.getNickname(),
                        Action.LOGIN
                )
        );

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken  = jwtProvider.generateRefreshToken(user);

        return LocalLoginResponseDto.from(user, accessToken, refreshToken);
    }

    @Transactional
    public void deleteUser(User user) {
        userHistoryRepository.save(
                UserHistory.create(
                        user.getId(),
                        user.getUsername(),
                        user.getNickname(),
                        Action.DELETE
                )
        );

        userRepository.delete(user); // 하드딜리트
    }


}
