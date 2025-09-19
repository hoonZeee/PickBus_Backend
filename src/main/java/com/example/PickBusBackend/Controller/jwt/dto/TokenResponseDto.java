package com.example.PickBusBackend.Controller.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
}
