package com.example.PickBusBackend.repository;

import com.example.PickBusBackend.domain.Favorite;
import com.example.PickBusBackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    void deleteByUserAndBusStopIdAndLineNo(User user, String busStopId, String lineNo);
    void deleteAllByUserId(Long userId);
}
