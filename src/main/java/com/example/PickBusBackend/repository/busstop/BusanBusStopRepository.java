package com.example.PickBusBackend.repository.busstop;

import com.example.PickBusBackend.repository.busstop.entity.BusanBusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusanBusStopRepository extends JpaRepository<BusanBusStop, String> {
}
