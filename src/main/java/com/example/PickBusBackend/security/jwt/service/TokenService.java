package com.example.PickBusBackend.security.jwt.service;

import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;

    private static final String RT_PREFIX = "RT:";

    public String generateAccessToken(User user) {
        return jwtProvider.generateAccessToken(user);
    }

    public String generateRefreshToken(User user) {
        String refreshToken = jwtProvider.generateRefreshToken(user);
        redisTemplate.opsForValue().set(
                RT_PREFIX + user.getUsername(),
                refreshToken,
                jwtProvider.getRefreshExpiration(),
                TimeUnit.MILLISECONDS
        );
        return refreshToken;
    }

    public boolean validationRefreshToken(String username, String refreshToken) {
        String stored = redisTemplate.opsForValue().get(RT_PREFIX + username);
        return stored != null && stored.equals(refreshToken);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(RT_PREFIX + username);
    }
}
