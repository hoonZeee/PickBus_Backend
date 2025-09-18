package com.example.PickBusBackend.repository.busstop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeoulBusStop {

    @Id
    private String stopId;

    private String busStopName;
    private String nextStopName;

}
