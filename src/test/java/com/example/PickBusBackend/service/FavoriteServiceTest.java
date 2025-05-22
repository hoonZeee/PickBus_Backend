package com.example.PickBusBackend.service;

import com.example.PickBusBackend.domain.Favorite;
import com.example.PickBusBackend.domain.User;
import com.example.PickBusBackend.repository.FavoriteRepository;
import com.example.PickBusBackend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FavoriteServiceTest {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User("testuser", "pw", "test@email.com");
        userRepository.save(testUser);
    }

    @Test
    void testAddFavorite_Success() {
        favoriteService.addFavorite(testUser, "501060000", "연제구3", "수정역", "하단");

        List<Favorite> list = favoriteRepository.findByUser(testUser);
        assertEquals(1, list.size());
    }

    @Test
    void testAddFavorite_Duplicate() {
        favoriteService.addFavorite(testUser, "501060000", "연제구3", "수정역", "하단");

        assertThrows(DataIntegrityViolationException.class, () -> {
            favoriteService.addFavorite(testUser, "501060000", "연제구3", "수정역", "하단");
        });
    }

    @Test
    void testRemoveFavorite_Success() {
        favoriteService.addFavorite(testUser, "501060000", "연제구3","수정역", "하단");
        favoriteService.removeFavorite(testUser, "501060000", "연제구3");

        List<Favorite> list = favoriteRepository.findByUser(testUser);
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveFavorite_NonExistent() {
        assertDoesNotThrow(() -> {
            favoriteService.removeFavorite(testUser, "999999999", "없는버스");
        });
    }
}
