package com.example.PickBusBackend.Controller.jwt;

import com.example.PickBusBackend.Controller.jwt.dto.TokenResponseDto;
import com.example.PickBusBackend.exception.domain.UserException;
import com.example.PickBusBackend.exception.error.UserErrorCode;
import com.example.PickBusBackend.repository.user.UserRepository;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.security.jwt.JwtProvider;
import com.example.PickBusBackend.security.jwt.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestHeader("Authorization") String bearerToken,
                                     @RequestHeader("Refresh") String refreshToken) {

        try{
            String accessToken = bearerToken.replace("Bearer", "");

            Claims claims = jwtProvider.getClaims(accessToken);
            String username = claims.getSubject();

            boolean valid = tokenService.validationRefreshToken(username, refreshToken);
            if(!valid) {
                return ResponseEntity.status(401).body("RefreshToken 이 유효하지 않음");
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new UserException(UserErrorCode.USER_NOT_FOUND));

            String newAccessToken = tokenService.generateAccessToken(user);
            String newRefreshToken = tokenService.generateRefreshToken(user);

            return ResponseEntity.ok()
                    .body(new TokenResponseDto(newAccessToken, newRefreshToken));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("토큰이 유효하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러");
        }
    }
}
