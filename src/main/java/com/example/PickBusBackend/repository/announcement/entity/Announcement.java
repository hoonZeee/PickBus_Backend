package com.example.PickBusBackend.repository.announcement.entity;

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
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;
    private Long viewCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Announcement create(User user, String title, String content) {
        return new Announcement(
                null,
                user,
                title,
                content,
                0L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
