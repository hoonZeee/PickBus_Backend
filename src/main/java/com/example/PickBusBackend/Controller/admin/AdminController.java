package com.example.PickBusBackend.Controller.admin;

import com.example.PickBusBackend.Controller.user.dto.request.LocalSignUpRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalSignUpResponseDto;
import com.example.PickBusBackend.service.application.admin.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<LocalSignUpResponseDto> signUp(
            @Valid @RequestBody LocalSignUpRequestDto request) {

        LocalSignUpResponseDto response = adminService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> checkAdminAccess() {
        return ResponseEntity.ok("어드민 권한 확인 완료");
    }
}
