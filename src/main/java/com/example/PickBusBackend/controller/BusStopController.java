package com.example.PickBusBackend.controller;

import com.example.PickBusBackend.domain.BusStop;
import com.example.PickBusBackend.service.BusStopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busstops")
public class BusStopController {
    private final BusStopService busStopService;

    public BusStopController(BusStopService busStopService) {
        this.busStopService = busStopService;
    }

    @GetMapping
    public List<BusStop> searchStops(@RequestParam String query) {
        return busStopService.searchBusStops(query);
    }
}
