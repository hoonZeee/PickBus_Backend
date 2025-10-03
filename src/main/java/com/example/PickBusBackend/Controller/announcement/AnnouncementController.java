package com.example.PickBusBackend.Controller.announcement;

import com.example.PickBusBackend.Controller.announcement.dto.request.AnnouncementRequestDto;
import com.example.PickBusBackend.Controller.announcement.dto.response.AnnouncementResponseDto;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.security.principal.CustomUserDetails;
import com.example.PickBusBackend.service.application.announcement.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnnouncementResponseDto> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AnnouncementRequestDto request) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(announcementService.create(user, request));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDto>> findAll() {
        return ResponseEntity.ok(announcementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnnouncementResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequestDto request) {
        return ResponseEntity.ok(announcementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
