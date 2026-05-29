package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.AnalyticsItemProjection;
import io.DestinySource.Flowstate.dto.DashboardResponseDTO;
import io.DestinySource.Flowstate.exception.ResourceNotFoundException;
import io.DestinySource.Flowstate.exception.UnauthorizedException;
import io.DestinySource.Flowstate.model.Site;
import io.DestinySource.Flowstate.model.User;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import io.DestinySource.Flowstate.repository.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final SiteRepository siteRepository;
    private final AnalyticsRepository analyticsRepository;

    public DashboardService(SiteRepository siteRepository, AnalyticsRepository analyticsRepository) {
        this.siteRepository = siteRepository;
        this.analyticsRepository = analyticsRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponseDTO getWebsiteStats(String host, String cutOff, User user) {
        Site site = siteRepository.findBySiteHost(host)
                .orElseThrow(() -> new ResourceNotFoundException("Host '" + host + "' niet gevonden."));

        if (!site.isVerified()) {
            throw new UnauthorizedException("Kan data niet ophalen. Site is niet geverifieerd.");
        }

        LocalDateTime cutOffDate = calculateCutOffDate(cutOff);

        List<AnalyticsItemProjection> pages = analyticsRepository.getStatsByPages(host, cutOffDate);
        List<AnalyticsItemProjection> browser = analyticsRepository.getStatsByBrowser(host, cutOffDate);
        List<AnalyticsItemProjection> os = analyticsRepository.getStatsByOs(host, cutOffDate);
        List<AnalyticsItemProjection> device = analyticsRepository.getStatsByDevice(host, cutOffDate);

        return new DashboardResponseDTO(host, site.isVerified(), pages, browser, os, device);
    }

    private LocalDateTime calculateCutOffDate(String cutOff) {
        return switch (cutOff.toLowerCase()) {
            case "24hours" -> LocalDateTime.now().minusDays(1);
            case "30days" -> LocalDateTime.now().minusDays(30);
            default -> LocalDateTime.now().minusDays(7);
        };
    }
}