package io.DestinySource.Flowstate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "analytics",
        indexes = {
        @Index(name = "idx_analytics_site_date_path", columnList = "site_id, created_at, path"),

        @Index(name = "idx_analytics_duplicate_check", columnList = "visitor_id, event_name, path, created_at")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Site site;

    @Column(name = "visitor_id")
    private String visitorId;

    @Column(name = "path")
    private String path;

    @Column(name = "referrer")
    private String referrer;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @Column(name = "browser")
    private String browser;

    @Column(name = "os")
    private String os;

    @Column(name = "device")
    private String device;

    @Column(name = "duration_seconds")
    private Long durationSeconds;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
