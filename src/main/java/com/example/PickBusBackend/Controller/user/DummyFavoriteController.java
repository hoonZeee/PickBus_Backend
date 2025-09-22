package com.example.PickBusBackend.Controller.user;
import com.example.PickBusBackend.repository.user.UserRepository;
import com.example.PickBusBackend.repository.user.entity.User;
import com.example.PickBusBackend.repository.user.entity.Favorite;
import com.example.PickBusBackend.repository.user.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DummyFavoriteController {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    // 누구나 호출할 수 있도록 테스트용 → 실제 서비스엔 절대 두면 안 됨!
    @GetMapping("/api/dummy/users-favorites")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> insertDummyUsersAndFavorites() {
        int userCount = 1000;
        int favoritesPerUser = 1000;

        for (int u = 0; u < userCount; u++) {
            User dummyUser = new User( null, "nick" + u, "user" + u + "@test.com", "pw1234", "SEOUL",null,null,null,null,null);
            userRepository.save(dummyUser);

            List<Favorite> batch = new ArrayList<>();
            for (int f = 0; f < favoritesPerUser; f++) {
                batch.add(Favorite.create(
                        dummyUser,
                        "stop-" + f,
                        "정류장-" + (f % 1000),
                        "line-" + (f % 50),
                        "next-" + (f % 2000)
                ));

                if (batch.size() == 1000) {
                    favoriteRepository.saveAll(batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) favoriteRepository.saveAll(batch);

            log.info("User {} 의 즐겨찾기 {}개 저장 완료", dummyUser.getUsername(), favoritesPerUser);
        }

        return ResponseEntity.ok(userCount + "명 × " + favoritesPerUser + "개 즐겨찾기 삽입 완료");
    }
}
