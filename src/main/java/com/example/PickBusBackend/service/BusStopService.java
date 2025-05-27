package com.example.PickBusBackend.service;

import com.example.PickBusBackend.domain.BusStop;
import com.example.PickBusBackend.repository.BusStopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopService {
    private final BusStopRepository busStopRepository;

    public BusStopService(BusStopRepository busStopRepository) {
        this.busStopRepository = busStopRepository;
    }

    public List<BusStop> searchBusStops(String query) {
        return busStopRepository.findByStopNameContaining(query);
    }
}