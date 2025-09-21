package com.example.PickBusBackend.Controller.user;

import com.example.PickBusBackend.Controller.user.dto.request.LocalLoginRequestDto;
import com.example.PickBusBackend.Controller.user.dto.request.LocalSignUpRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalLoginResponseDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalSignUpResponseDto;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.security.principal.CustomUserDetails;
import com.example.PickBusBackend.service.application.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<LocalSignUpResponseDto> signUp(
            @Valid @RequestBody LocalSignUpRequestDto request) {

        LocalSignUpResponseDto response = userService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LocalLoginResponseDto> login(
            @Valid @RequestBody LocalLoginRequestDto request) {
        LocalLoginResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        User user = customUserDetails.getUser();
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }



}
