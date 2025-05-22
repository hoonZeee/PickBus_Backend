package com.example.PickBusBackend.controller;


import com.example.PickBusBackend.domain.Favorite;
import com.example.PickBusBackend.domain.User;
import com.example.PickBusBackend.security.JwtUtil;
import com.example.PickBusBackend.service.FavoriteService;
import com.example.PickBusBackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService, JwtUtil jwtUtil, UserService userService) {
        this.favoriteService = favoriteService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getFavorites(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.validateToken(token);

        User user = userService.getByUsername(username); // ✅ getByUsername 사용
        List<Favorite> list = favoriteService.getFavorites(user); // ✅ User 기반 검색
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestHeader("Authorization") String authHeader,
                                         @RequestBody Map<String, String> body) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.validateToken(token);
        User user = userService.getByUsername(username);
        //여기 direction 받아서 한줄추가하고

        String stopId = body.get("busStopId");
        String lineNo = body.get("lineNo");
        String stopName = body.get("busStopName");
        String nextStopName = body.get("nextStopName");

        favoriteService.addFavorite(user, stopId, lineNo, stopName, nextStopName); // ✅ 호출 변경
        return ResponseEntity.ok("즐겨찾기 등록 완료");
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@RequestHeader("Authorization") String authHeader,
                                            @RequestParam String busStopId,
                                            @RequestParam String lineNo) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.validateToken(token);
        User user = userService.getByUsername(username);

        favoriteService.removeFavorite(user, busStopId, lineNo);
        return ResponseEntity.ok("즐겨찾기 해제 완료");
    }



}
