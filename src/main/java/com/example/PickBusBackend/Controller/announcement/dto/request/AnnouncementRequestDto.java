package com.example.PickBusBackend.Controller.announcement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
