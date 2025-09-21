package com.example.PickBusBackend.repository.user;


import com.example.PickBusBackend.repository.user.entity.Favorite;
import com.example.PickBusBackend.repository.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndStopIdAndBusLineNo(User user, String stopId, String busLineNo);

    List<Favorite> findAllByUser(User user);

    // FavoriteRepository.java
    Optional<Favorite> findByUserAndStopIdAndBusLineNo(User user, String stopId, String busLineNo);

}
