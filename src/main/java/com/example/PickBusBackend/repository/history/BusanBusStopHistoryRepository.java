package com.example.PickBusBackend.repository.history;

import com.example.PickBusBackend.repository.history.entity.BusanBusStopHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusanBusStopHistoryRepository extends JpaRepository<BusanBusStopHistory, Long> {
}
