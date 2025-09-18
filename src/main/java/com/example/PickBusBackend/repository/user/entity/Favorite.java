package com.example.PickBusBackend.repository.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String stopId;
    private String busStopName;
    private String busLineNo;
    private String nextStopName;

    private LocalDateTime createdAt;

    public static Favorite create(User user, String stopId, String busStopName, String busLineNo, String nextStopName) {
        return new Favorite(
                null,
                user,
                stopId,
                busStopName,
                busLineNo,
                nextStopName,
                LocalDateTime.now()
        );
    }
}
