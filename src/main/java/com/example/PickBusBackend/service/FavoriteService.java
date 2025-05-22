package com.example.PickBusBackend.service;


import com.example.PickBusBackend.domain.Favorite;
import com.example.PickBusBackend.domain.User;
import com.example.PickBusBackend.repository.FavoriteRepository;
import com.example.PickBusBackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    public List<Favorite> getFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    @Transactional
    public void addFavorite(User user, String busStopId, String lineNo, String busStopName, String nextStopName) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBusStopId(busStopId);
        favorite.setLineNo(lineNo);
        favorite.setBusStopName(busStopName);
        //System.out.println(" 저장 전 체크: " + favorite.getUser().getUsername());
        favorite.setNextStopName(nextStopName);
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(User user, String busStopId, String lineNo) {
        favoriteRepository.deleteByUserAndBusStopIdAndLineNo(user, busStopId, lineNo);
    }



}
