package com.example.PickBusBackend.Controller.busstop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusStopResponseDto {

    private String stopId;
    private String BusStopName;
    private String nextStopName;

}
