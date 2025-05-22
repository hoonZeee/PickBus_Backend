package com.example.PickBusBackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // ✅ 실제 user_id 외래키 연결

    @Column(name = "bus_stop_id", nullable = false)
    private String busStopId;

    @Column(name = "bus_stop_name", nullable = false)
    private String busStopName;

    @Column(name = "bus_line_no", nullable = false)
    private String lineNo;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "next_stop_name", nullable = false)
    private String nextStopName;

}
