package com.example.PickBusBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/arrival")
@CrossOrigin
public class BusArrivalProxyController {

    @GetMapping
    public ResponseEntity<String> proxyArrival(@RequestParam String busStopId) {
        String serviceKey = "TF7Mku+SzYBCUtjel7ketfVE8uKj1gnPjH0fxQG1amxTSmZHTaIWBCr5qqOYQ7/y5CHFp/vmmmeW2ITP53yHw==";

        String url = UriComponentsBuilder
                .fromHttpUrl("http://apis.data.go.kr/6260000/BusanBIMS/stopArrByBstopid")
                .queryParam("serviceKey", serviceKey)
                .queryParam("bstopid", busStopId)
                .build(false) // ← 인코딩 하지 말고 그대로 써라
                .toUriString();

        System.out.println("👉 최종 요청 URL: " + url);

        RestTemplate restTemplate = new RestTemplate();
        String xml = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(xml);
    }
}
