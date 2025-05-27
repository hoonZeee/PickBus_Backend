package com.example.PickBusBackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "busan_bus_stop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusStop {
    @Id
    private String stopId;
    private String stopName;
    private String nextStopName;
}
