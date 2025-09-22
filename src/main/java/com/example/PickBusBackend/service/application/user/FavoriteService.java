package com.example.PickBusBackend.service.application.user;

import com.example.PickBusBackend.Controller.user.dto.request.FavoriteCreateRequestDto;
import com.example.PickBusBackend.Controller.user.dto.response.FavoriteResponseDto;
import com.example.PickBusBackend.exception.domain.UserException;
import com.example.PickBusBackend.exception.error.UserErrorCode;
import com.example.PickBusBackend.repository.user.FavoriteRepository;
import com.example.PickBusBackend.repository.user.entity.Favorite;
import com.example.PickBusBackend.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    @CacheEvict(value = "favorites", key = "#user.id")
    public FavoriteResponseDto addFavorite(User user, FavoriteCreateRequestDto request) {
        if(user == null) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        boolean exists = favoriteRepository.existsByUserAndStopIdAndBusLineNo(
                user,
                request.getStopId(),
                request.getBusLineNo()
        );

        if(exists) {
            throw new UserException(UserErrorCode.ALREADY_IN_FAVORITE);
        }

        Favorite favorite = Favorite.create(
                user,
                request.getStopId(),
                request.getBusStopName(),
                request.getBusLineNo(),
                request.getNextStopName()
        );

        Favorite saved = favoriteRepository.save(favorite);
        return FavoriteResponseDto.from(saved);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "favorites", key = "#user.id")
    public List<FavoriteResponseDto> getFavorites(User user) {
        List<Favorite> list = favoriteRepository.findAllByUser(user);
        return list.stream()
                .map(FavoriteResponseDto::from)
                .toList();
    }


    @Transactional
    @CacheEvict(value = "favorites", key = "#user.id")
    public void deleteFavorite(User user, Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기를 찾을 수 없습니다."));

        if (!favorite.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 즐겨찾기만 삭제할 수 있습니다.");
        }

        favoriteRepository.delete(favorite); // 즐찾은 하드 딜리트
    }

    @Transactional
    @CacheEvict(value = "favorites", key = "#user.id")
    public void deleteByStopIdAndLineNo(User user, String stopId, String busLineNo) {
        Favorite favorite = favoriteRepository.findByUserAndStopIdAndBusLineNo(user, stopId, busLineNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 즐겨찾기를 찾을 수 없습니다."));
        favoriteRepository.delete(favorite);
    }


}
