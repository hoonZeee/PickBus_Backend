package com.example.PickBusBackend.service.application.announcement;

import com.example.PickBusBackend.Controller.announcement.dto.request.AnnouncementRequestDto;
import com.example.PickBusBackend.Controller.announcement.dto.response.AnnouncementResponseDto;
import com.example.PickBusBackend.exception.domain.AnnouncementException;
import com.example.PickBusBackend.exception.error.AnnouncementErrorCode;
import com.example.PickBusBackend.repository.announcement.AnnouncementRepository;
import com.example.PickBusBackend.repository.announcement.entity.Announcement;
import com.example.PickBusBackend.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Transactional
    public AnnouncementResponseDto create(User user, AnnouncementRequestDto request) {
        Announcement announcement = Announcement.create(
                user,
                request.getTitle(),
                request.getContent()
        );
        announcementRepository.save(announcement);
        return AnnouncementResponseDto.from(announcement);
    }

    @Transactional(readOnly = true)
    public List<AnnouncementResponseDto> findAll() {
        return announcementRepository.findAll().stream()
                .map(AnnouncementResponseDto::from)
                .toList();
    }

    @Transactional
    public AnnouncementResponseDto findById(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(()-> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

        announcementRepository.increaseViewCount(id);

        return AnnouncementResponseDto.from(announcement);
    }

    @Transactional
    public AnnouncementResponseDto update(Long id, AnnouncementRequestDto request) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(()-> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

        announcement.update(
                request.getTitle(),
                request.getContent()
        );

        return AnnouncementResponseDto.from(announcement);
    }

    @Transactional
    public void delete(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(()-> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

        announcementRepository.delete(announcement);
    }
}
