package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.AnalyticsItemProjection;
import io.DestinySource.Flowstate.exception.ResourceNotFoundException;
import io.DestinySource.Flowstate.exception.UnauthorizedException;
import io.DestinySource.Flowstate.model.Analytics;
import io.DestinySource.Flowstate.model.Site;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import io.DestinySource.Flowstate.repository.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final SiteRepository siteRepository;
    private final AnalyticsRepository analyticsRepository;

    public DashboardService(SiteRepository siteRepository, AnalyticsRepository analyticsRepository) {
        this.siteRepository = siteRepository;
        this.analyticsRepository = analyticsRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, List<AnalyticsItemProjection>> getWebsiteStats(String host, String cutOff) {
        Site site = siteRepository.findBySiteHost(host)
                .orElseThrow(() -> new ResourceNotFoundException("Host '" + host + "' niet gevonden."));

        if (!site.isVerified()) {
            throw new UnauthorizedException("Kan data niet ophalen. Site is niet geverifieerd.");
        }

        LocalDateTime cutOffDate = calculateCutOffDate(cutOff);

        Map<String, List<AnalyticsItemProjection>> dashboardData = new HashMap<>();
        dashboardData.put("hostname", analyticsRepository.getStatsByHostname(host));

        dashboardData.put("pages", analyticsRepository.getStatsByPages(host, cutOffDate));
        
        dashboardData.put("browser", analyticsRepository.getStatsByBrowser(host, cutOffDate));
        dashboardData.put("os", analyticsRepository.getStatsByOs(host, cutOffDate));
        dashboardData.put("device", analyticsRepository.getStatsByDevice(host, cutOffDate));
        return dashboardData;
    }

    private LocalDateTime calculateCutOffDate(String cutOff) {
        return switch (cutOff.toLowerCase()) {
            case "24hours" -> LocalDateTime.now().minusDays(1);
            case "30days" -> LocalDateTime.now().minusDays(30);
            default -> LocalDateTime.now().minusDays(7);
        };
    }
}