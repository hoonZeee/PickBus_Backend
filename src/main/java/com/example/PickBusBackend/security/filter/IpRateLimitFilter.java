package com.example.PickBusBackend.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IP 단위 Rate Limit (In-Memory 구현)
 * - /api/users/login, /api/users/signup 요청 과도 시 차단
 */
public class IpRateLimitFilter extends OncePerRequestFilter {

    private final Map<String, List<LocalDateTime>> requestLogs = new ConcurrentHashMap<>();

    private static final int LIMIT = 10;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.equals("/api/users/login") || path.equals("/api/users/signup")) {
            String ip = request.getRemoteAddr();
            LocalDateTime now = LocalDateTime.now();

            requestLogs.putIfAbsent(ip, new ArrayList<>());
            List<LocalDateTime> logs = requestLogs.get(ip);

            logs.removeIf(time -> time.isBefore(now.minus(WINDOW)));

            logs.add(now);
            // 다 지워져서 비어 있으면 Map에서 키 제거 (메모리 누수 방지)
            if (logs.isEmpty()) {
                requestLogs.remove(ip);
            }

            if (logs.size() > LIMIT) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
