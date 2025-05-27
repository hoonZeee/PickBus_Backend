package com.example.PickBusBackend.service;

import com.example.PickBusBackend.domain.User;
import com.example.PickBusBackend.repository.FavoriteRepository;
import com.example.PickBusBackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder, favoriteService);
    }

    @Test
    void 회원가입_성공() {
        when(userRepository.findByUsername("jihun")).thenReturn(Optional.empty());
        when(userRepository.existsByEmail("temp@email.com")).thenReturn(false);

        String result = userService.signup("jihun", "1234", "temp@email.com");

        assertEquals("회원가입 성공", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 회원가입_중복_아이디() {
        when(userRepository.findByUsername("jihun")).thenReturn(Optional.of(new User("jihun", "1234", "temp@email.com")));

        String result = userService.signup("jihun", "1234", "temp@email.com");

        assertEquals("이미 존재하는 아이디", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void 회원가입_중복_이메일() {
        when(userRepository.findByUsername("jihun")).thenReturn(Optional.empty());
        when(userRepository.existsByEmail("temp@email.com")).thenReturn(true);

        String result = userService.signup("jihun", "1234", "temp@email.com");

        assertEquals("이미 존재하는 이메일", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void 로그인_성공() {
        User mockUser = new User("jihun", "encoded_pw", "temp@email.com");
        when(userRepository.findByUsername("jihun")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("1234", "encoded_pw")).thenReturn(true);

        String result = userService.login("jihun", "1234");

        assertEquals("로그인 성공", result);
    }

    @Test
    void 로그인_실패_비밀번호불일치() {
        User mockUser = new User("jihun", "encoded_pw", "temp@email.com");
        when(userRepository.findByUsername("jihun")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("1234", "encoded_pw")).thenReturn(false);

        String result = userService.login("jihun", "1234");

        assertEquals("비밀번호가 일치하지 않습니다.", result);
    }

    @Test
    void 로그인_실패_없는아이디() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        String result = userService.login("unknown", "1234");

        assertEquals("존재하지 않는 아이디입니다.", result);
    }
}
