package com.example.PickBusBackend.Controller.announcement.dto.response;

import com.example.PickBusBackend.repository.announcement.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnnouncementResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnnouncementResponseDto from(Announcement announcement) {
        return new AnnouncementResponseDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getViewCount(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt()
        );
    }
}
