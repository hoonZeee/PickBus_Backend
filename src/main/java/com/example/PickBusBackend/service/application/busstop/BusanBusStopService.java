package com.example.PickBusBackend.service.application.busstop;

import com.example.PickBusBackend.repository.busstop.BusanBusStopRepository;
import com.example.PickBusBackend.repository.busstop.entity.BusanBusStop;
import com.example.PickBusBackend.repository.history.BusanBusStopHistoryRepository;
import com.example.PickBusBackend.repository.history.entity.BusanBusStopHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BusanBusStopService {

    private final BusanBusStopRepository busanBusStopRepository;
    private final BusanBusStopHistoryRepository busanBusStopHistoryRepository;

    @Value("${busan.api.key}")
    private String busanApiKey;

    public int updateBusStops() {
        System.out.println("[BusanBusStopService] 정류장 갱신 시작");

        try {
            // 1. 모든 정류장 수집
            Map<String, String> allStops = getAllBusStops();
            System.out.println("전체 정류장 수집 완료: " + allStops.size() + "개");

            // 2. 노선별 nextStop 계산
            Map<String, Map<String, Integer>> nextStopVotes = new HashMap<>();
            List<String> lineIds = getAllLineIds();
            System.out.println("수집된 노선 개수: " + lineIds.size());

            for (String lineId : lineIds) {
                List<BusanBusStopDto> stops = fetchStopsByLineId(lineId);

                for (int i = 0; i < stops.size(); i++) {
                    String currentId = stops.get(i).id();
                    String nextName = (i + 1 < stops.size()) ? stops.get(i + 1).name() : "종점";

                    nextStopVotes.putIfAbsent(currentId, new HashMap<>());
                    nextStopVotes.get(currentId).merge(nextName, 1, Integer::sum);
                }
            }

            // 3. DB 저장
            List<BusanBusStop> entities = new ArrayList<>();
            for (String stopId : allStops.keySet()) {
                String stopName = allStops.get(stopId);
                String nextStopName = "정보 없음";

                if (nextStopVotes.containsKey(stopId)) {
                    Map<String, Integer> candidates = nextStopVotes.get(stopId);
                    nextStopName = Collections.max(candidates.entrySet(), Map.Entry.comparingByValue()).getKey();
                }

                entities.add(BusanBusStop.of(stopId, stopName, nextStopName));
            }

            busanBusStopRepository.deleteAllInBatch();
            busanBusStopRepository.saveAll(entities);

            // 성공 히스토리 기록
            busanBusStopHistoryRepository.save(BusanBusStopHistory.success(entities.size()));

            return entities.size();

        } catch (Exception e) {
            // 실패 히스토리 기록
            busanBusStopHistoryRepository.save(BusanBusStopHistory.fail(e.getMessage()));
            throw e;
        }
    }

    private Map<String, String> getAllBusStops() {
        Map<String, String> stopMap = new HashMap<>();
        try {
            for (int page = 1; page <= 100; page++) { // 안전하게 100까지 반복
                String urlStr = "http://apis.data.go.kr/6260000/BusanBIMS/busStopList"
                        + "?serviceKey=" + busanApiKey
                        + "&pageNo=" + page
                        + "&numOfRows=1000";
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
                conn.disconnect();

                NodeList items = doc.getElementsByTagName("item");
                if (items.getLength() == 0) break;

                for (int i = 0; i < items.getLength(); i++) {
                    Element item = (Element) items.item(i);
                    String id = getTag(item, "bstopid");
                    String name = getTag(item, "bstopnm");
                    if (!id.isEmpty() && !name.isEmpty()) {
                        stopMap.put(id.trim(), name.trim());
                    }
                }
                Thread.sleep(50); // 서버 부하 방지
            }
        } catch (Exception e) {
            System.out.println("전체 정류장 수집 실패: " + e.getMessage());
        }
        return stopMap;
    }

    private List<String> getAllLineIds() {
        List<String> ids = new ArrayList<>();
        try {
            String urlStr = "http://apis.data.go.kr/6260000/BusanBIMS/busInfo?serviceKey=" + busanApiKey;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
            conn.disconnect();

            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                String lineid = getTag(item, "lineid");
                if (!lineid.isEmpty()) ids.add(lineid);
            }
        } catch (Exception e) {
            System.out.println("lineid 수집 실패: " + e.getMessage());
        }
        return ids;
    }

    private List<BusanBusStopDto> fetchStopsByLineId(String lineId) {
        List<BusanBusStopDto> stops = new ArrayList<>();
        try {
            String urlStr = "http://apis.data.go.kr/6260000/BusanBIMS/busInfoByRouteId"
                    + "?serviceKey=" + busanApiKey
                    + "&lineid=" + lineId;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
            conn.disconnect();

            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                String id = getTag(item, "nodeid");
                String name = getTag(item, "bstopnm");
                if (!id.isEmpty() && !name.isEmpty()) {
                    stops.add(new BusanBusStopDto(id.trim(), name.trim()));
                }
            }
        } catch (Exception e) {
            System.out.println("정류장 수집 실패 (" + lineId + "): " + e.getMessage());
        }
        return stops;
    }

    private String getTag(Element e, String tag) {
        NodeList list = e.getElementsByTagName(tag);
        return list.getLength() > 0 ? list.item(0).getTextContent().trim() : "";
    }

    record BusanBusStopDto(String id, String name) {}
}
