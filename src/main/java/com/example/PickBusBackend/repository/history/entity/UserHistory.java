package com.example.PickBusBackend.repository.history.entity;

import com.example.PickBusBackend.repository.history.entity.vo.Action;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Action action;

    private LocalDateTime createdAt;
    
    public  static UserHistory create(Long userId, String username,String nickname, Action action) {
        return new UserHistory(
                null,
                userId,
                username,
                nickname,
                action,
                LocalDateTime.now()
        );
    }
}
