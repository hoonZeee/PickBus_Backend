package com.example.PickBusBackend.Controller.user.dto.response;

import com.example.PickBusBackend.repository.user.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FavoriteResponseDto {

    private Long id;
    private String stopId;
    private String busStopName;
    private String busLineNo;
    private String nextStopName;
    private LocalDateTime createdAt;

    public static FavoriteResponseDto from(Favorite favorite) {
        return new FavoriteResponseDto(
                favorite.getId(),
                favorite.getStopId(),
                favorite.getBusStopName(),
                favorite.getBusLineNo(),
                favorite.getNextStopName(),
                favorite.getCreatedAt()
        );
    }
}
