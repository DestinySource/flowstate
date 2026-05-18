package io.DestinySource.Flowstate.repository;

import io.DestinySource.Flowstate.model.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    List<Analytics> findAllByOrderByCreatedAtDesc();

    List<Analytics> findBySite_SiteUrl(String siteUrl);

    boolean existsByVisitorIdAndEventNameAndUrlAndCreatedAtAfter(
            String visitorId,
            String eventName,
            String url,
            LocalDateTime timestamp
    );
}