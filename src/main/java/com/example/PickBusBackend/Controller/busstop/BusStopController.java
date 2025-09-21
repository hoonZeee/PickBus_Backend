package com.example.PickBusBackend.Controller.busstop;

import com.example.PickBusBackend.Controller.busstop.dto.response.BusStopResponseDto;
import com.example.PickBusBackend.service.application.busstop.BusStopService;
import com.example.PickBusBackend.service.application.busstop.BusanBusStopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus-stops")
public class BusStopController {

    private final BusanBusStopService busanBusStopService;
    private final BusStopService busStopService;

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBusStops() {
        int count = busanBusStopService.updateBusStops();
        return ResponseEntity.ok("부산 버스 정류장 " + count + "개 갱신 완료");
    }

    @GetMapping
    public ResponseEntity<List<BusStopResponseDto>> searchBusStops(@RequestParam String query) {
        return ResponseEntity.ok(busStopService.searchBusStops(query));
    }
}
