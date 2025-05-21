package com.example.PickBusBackend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void 토큰_생성_및_검증_성공() {
        String username = "jihun";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.validateToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void 토큰_검증_실패_잘못된_토큰() {
        String invalidToken = "invalid.token.value";

        String result = jwtUtil.validateToken(invalidToken);
        assertNull(result); // 검증 실패하면 null 반환
    }
}
