package com.example.PickBusBackend.repository;

import com.example.PickBusBackend.domain.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusStopRepository extends JpaRepository<BusStop, String> {
    List<BusStop> findByStopNameContaining(String query);
}