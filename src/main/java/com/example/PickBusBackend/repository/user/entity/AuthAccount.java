package com.example.PickBusBackend.repository.user.entity;

import com.example.PickBusBackend.repository.user.entity.vo.Provider;
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
public class AuthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String providerUserId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AuthAccount create(User user, Provider provider, String providerUserId){
        return new AuthAccount(
                null,
                user,
                provider,
                providerUserId,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


}
