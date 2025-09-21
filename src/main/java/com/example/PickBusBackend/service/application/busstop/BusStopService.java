package com.example.PickBusBackend.service.application.busstop;

import com.example.PickBusBackend.Controller.busstop.dto.response.BusStopResponseDto;
import com.example.PickBusBackend.repository.busstop.BusanBusStopRepository;
import com.example.PickBusBackend.repository.busstop.entity.BusanBusStop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusStopService {
    private final BusanBusStopRepository busanBusStopRepository;

    public List<BusStopResponseDto> searchBusStops(String keyword) {
        return busanBusStopRepository.findByBusStopNameContaining(keyword).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BusStopResponseDto toDto(BusanBusStop stop) {
        return new BusStopResponseDto(
                stop.getStopId(),
                stop.getBusStopName(),
                stop.getNextStopName()
        );
    }
}
