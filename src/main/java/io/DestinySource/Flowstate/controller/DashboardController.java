package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.AnalyticsItemProjection;
import io.DestinySource.Flowstate.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(
            @RequestParam("host") String host,
            @RequestParam("cutOff") String cutOff) {

            Map<String, List<AnalyticsItemProjection>> response = dashboardService.getWebsiteStats(host, cutOff);
            return ResponseEntity.ok(response);
    }
    }