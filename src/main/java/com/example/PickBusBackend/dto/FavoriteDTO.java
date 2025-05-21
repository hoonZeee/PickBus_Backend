package com.example.PickBusBackend.dto;

public class FavoriteDTO {
    private String busStopId;
    private String lineNo;

    public String getBusStopId() { return busStopId; }
    public void setBusStopId(String busStopId) { this.busStopId = busStopId; }

    public String getLineNo() { return lineNo; }
    public void setLineNo(String lineNo) { this.lineNo = lineNo; }
}
