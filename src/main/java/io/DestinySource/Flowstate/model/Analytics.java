package io.DestinySource.Flowstate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "visitor_id")
    private String visitorId;

    @Column(name = "url")
    private String url;

    @Column(name = "referrer")
    private String referrer;

    @Column(name = "site_id")
    private String siteId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
