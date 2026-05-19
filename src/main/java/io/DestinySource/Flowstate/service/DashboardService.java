package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.AnalyticsItemProjection;
import io.DestinySource.Flowstate.exception.FlowstateExceptions;
import io.DestinySource.Flowstate.model.Site;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import io.DestinySource.Flowstate.repository.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Map<String, List<AnalyticsItemProjection>> getDashboardStats(String host) {
        Site site = siteRepository.findBySiteHost(host)
                .orElseThrow(() -> new FlowstateExceptions.ResourceNotFound("Host '" + host + "' niet gevonden."));

        if (!site.isVerified()) {
            throw new FlowstateExceptions.UnauthorizedException("Kan data niet ophalen. Site is niet geverifieerd.");
        }

        Map<String, List<AnalyticsItemProjection>> dashboardData = new HashMap<>();
        dashboardData.put("hostname", analyticsRepository.getStatsByHostname(host));
        dashboardData.put("pages", analyticsRepository.getStatsByPages(host));

        return dashboardData;
    }
}