package com.example.PickBusBackend.repository.busstop;

import com.example.PickBusBackend.repository.busstop.entity.BusanBusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface BusanBusStopRepository extends JpaRepository<BusanBusStop, String> {
    List<BusanBusStop> findByBusStopNameContaining(String keyword);
}
