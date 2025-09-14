package com.example.PickBusBackend.service;

import com.example.PickBusBackend.domain.User;
import com.example.PickBusBackend.repository.FavoriteRepository;
import com.example.PickBusBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class    UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FavoriteService favoriteService;


    //해싱비밀번호
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FavoriteService favoriteService ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.favoriteService = favoriteService;

    }



    public String signup(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "이미 존재하는 아이디";
        }
        if (userRepository.existsByEmail(email)) {
            return "이미 존재하는 이메일";
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email);
        userRepository.save(user);
        return "회원가입 성공";
    }


    //로그인
    public String login(String username, String rawPassword) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return "존재하지 않는 아이디입니다.";
        }

        if (!passwordEncoder.matches(rawPassword, user.get().getPassword())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        return "로그인 성공";
    }


    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("❌ 유저를 찾을 수 없습니다."));
    }


    public void deleteUserAndFavorites(String username) {
        User user = getByUsername(username);
        favoriteService.deleteAllFavoritesByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }











}
