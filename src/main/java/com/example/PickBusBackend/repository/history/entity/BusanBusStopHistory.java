package com.example.PickBusBackend.repository.history.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusanBusStopHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int updatedCount;

    private String status;

    private String message;

    private LocalDateTime executedAt;

    public static BusanBusStopHistory success(int count) {
        return new BusanBusStopHistory(null, count, "SUCCESS", "정상적으로 갱신됨", LocalDateTime.now());
    }

    public static BusanBusStopHistory fail(String errorMessage) {
        return new BusanBusStopHistory(null, 0, "FAIL", errorMessage, LocalDateTime.now());
    }
}
