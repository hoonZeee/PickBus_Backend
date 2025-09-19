package com.example.PickBusBackend.Controller.user;

import com.example.PickBusBackend.Controller.user.dto.request.LocalSignUpRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.LocalSignUpResponseDto;
import com.example.PickBusBackend.service.application.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
