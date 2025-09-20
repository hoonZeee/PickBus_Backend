package com.example.PickBusBackend.Controller.user.dto.response;

import com.example.PickBusBackend.repository.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocalLoginResponseDto {

    private Long id;
    private String username;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static LocalLoginResponseDto from(User user, String accessToken, String refreshToken) {
        return new LocalLoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                accessToken,
                refreshToken
        );
    }


}
