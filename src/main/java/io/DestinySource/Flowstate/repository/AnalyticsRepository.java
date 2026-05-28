package io.DestinySource.Flowstate.repository;

import io.DestinySource.Flowstate.dto.AnalyticsItemProjection;
import io.DestinySource.Flowstate.model.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    List<Analytics> findAllByOrderByCreatedAtDesc();

    List<Analytics> findBySite_SiteHost(String siteHost);

    boolean existsByVisitorIdAndEventNameAndPathAndCreatedAtAfter(
            String visitorId,
            String eventName,
            String path,
            LocalDateTime timestamp
    );

    @Query("SELECT s.siteHost AS name, COUNT(DISTINCT a.visitorId) AS uv " +
            "FROM Analytics a JOIN a.site s " +
            "WHERE s.siteHost = :siteHost " +
            "GROUP BY s.siteHost")
    List<AnalyticsItemProjection> getStatsByHostname(@Param("siteHost") String siteHost);

    @Query("SELECT a.path AS name, COUNT(DISTINCT a.visitorId) AS uv " +
            "FROM Analytics a " +
            "WHERE a.site.siteHost = :siteHost " +
            "AND a.createdAt >= :cutOffDate " +
            "GROUP BY a.path")
    List<AnalyticsItemProjection> getStatsByPages(
            @Param("siteHost") String siteHost,
            @Param("cutOffDate") LocalDateTime cutOffDate
    );

    @Query("SELECT COALESCE(a.browser, 'Unknown') AS name ,COUNT(DISTINCT a.visitorId) AS uv " +
            "FROM Analytics a " +
            "WHERE a.site.siteHost = :siteHost " +
            "AND a.createdAt >= :cutOffDate " +
            "GROUP BY a.browser")
    List<AnalyticsItemProjection> getStatsByBrowser(
            @Param("siteHost") String siteHost,
            @Param("cutOffDate") LocalDateTime cutOffDate
    );

    @Query("SELECT COALESCE(a.os, 'Unknown') AS name, COUNT(DISTINCT a.visitorId) AS uv " +
            "FROM Analytics a " +
            "WHERE a.site.siteHost = :siteHost " +
            "AND a.createdAt >= :cutOffDate " +
            "GROUP BY a.os")
    List<AnalyticsItemProjection> getStatsByOs(
            @Param("siteHost") String siteHost,
            @Param("cutOffDate") LocalDateTime cutOffDate
    );

    @Query("SELECT COALESCE(a.device, 'Unknown') AS name, COUNT(DISTINCT a.visitorId) AS uv " +
            "FROM Analytics a " +
            "WHERE a.site.siteHost = :siteHost " +
            "AND a.createdAt >= :cutOffDate " +
            "GROUP BY a.device")
    List<AnalyticsItemProjection> getStatsByDevice(
            @Param("siteHost") String siteHost,
            @Param("cutOffDate") LocalDateTime cutOffDate
    );
}