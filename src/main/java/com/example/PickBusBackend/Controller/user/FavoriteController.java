package com.example.PickBusBackend.Controller.user;

import com.example.PickBusBackend.Controller.user.dto.request.FavoriteCreateRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.FavoriteResponseDto;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.security.principal.CustomUserDetails;
import com.example.PickBusBackend.service.application.user.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteResponseDto>> getFavorites(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        long start = System.currentTimeMillis();

        User user = customUserDetails.getUser();
        List<FavoriteResponseDto> favorites = favoriteService.getFavorites(user);

        long end = System.currentTimeMillis();   // 끝나는 시간
        long duration = end - start;

        // 로그로 출력
        log.info("즐겨찾기 조회 API 실행 시간 = {} ms", duration);

        return ResponseEntity.ok(favorites);
    }


    @PostMapping
    public ResponseEntity<FavoriteResponseDto> addFavorite(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FavoriteCreateRequestDto request) {

        User user= customUserDetails.getUser();

        FavoriteResponseDto response = favoriteService.addFavorite(user, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id) {

        User user = customUserDetails.getUser();
        favoriteService.deleteFavorite(user, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByStopIdAndLineNo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String stopId,
            @RequestParam String busLineNo) {
        User user = userDetails.getUser();
        favoriteService.deleteByStopIdAndLineNo(user, stopId, busLineNo);
        return ResponseEntity.noContent().build();
    }
}
