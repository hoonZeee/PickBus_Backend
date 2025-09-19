package com.example.PickBusBackend.Controller.user.dto.request;

import com.example.PickBusBackend.repository.user.entity.vo.Region;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocalSignUpRequestDto {

    @NotBlank
    @Size(min = 3, max = 20, message = "아이디는 3~20자여야 합니다.")
    private String username;

    @NotBlank(message = "닉네임은 필수값입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotBlank
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "지역은 필수값입니다.")
    private Region region; // enum



}
