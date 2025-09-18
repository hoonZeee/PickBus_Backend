package com.example.PickBusBackend.repository.history.entity;

import com.example.PickBusBackend.repository.user.entity.User;
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
public class ArrivalCheckLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime checkedAt;

    public static ArrivalCheckLog create(User user) {
        return new ArrivalCheckLog(
                null,
                user,
                LocalDateTime.now()
        );
    }
}
