package com.example.PickBusBackend.Controller.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteCreateRequestDto {

    private String stopId;
    private String busStopName;
    private String busLineNo;
    private String nextStopName;

}
