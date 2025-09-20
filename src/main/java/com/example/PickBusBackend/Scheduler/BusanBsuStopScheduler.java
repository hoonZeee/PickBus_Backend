package com.example.PickBusBackend.Scheduler;

import com.example.PickBusBackend.service.application.busstop.BusanBusStopService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusanBsuStopScheduler {

    private final BusanBusStopService busanBusStopService;

    // 매달 1일 새벽 3시
    @Scheduled(cron = "0 0 3 1,15 * ?")
    public void scheduledUpdateBusStops() {
        System.out.println("[BusStopScheduler] 정기 갱신 시작");
        int count = busanBusStopService.updateBusStops();
        System.out.println("[BusStopScheduler] 정류장 " + count + "개 갱신 완료");
    }


}
