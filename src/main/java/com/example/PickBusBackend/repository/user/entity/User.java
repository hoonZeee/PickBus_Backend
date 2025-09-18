package com.example.PickBusBackend.repository.user.entity;

import com.example.PickBusBackend.repository.user.entity.vo.Region;
import com.example.PickBusBackend.repository.user.entity.vo.Role;
import com.example.PickBusBackend.repository.user.entity.vo.Status;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public static User create (String username, String email, String password, Role role, Region region){
        return new User(
                null,
                username,
                email,
                password,
                role,
                region,
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }






}
