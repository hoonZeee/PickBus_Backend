package com.example.PickBusBackend.Controller.busstop;

import com.example.PickBusBackend.service.application.busstop.BusanBusStopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus-stop")
public class BusStopController {

    private final BusanBusStopService busanBusStopService;

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBusStops() {
        int count = busanBusStopService.updateBusStops();
        return ResponseEntity.ok("부산 버스 정류장 " + count + "개 갱신 완료");

    }
}
