package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.DashboardResponseDTO;
import io.DestinySource.Flowstate.model.User;
import io.DestinySource.Flowstate.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardResponseDTO> getStats(
            @RequestParam("host") String host,
            @RequestParam("cutOff") String cutOff,
            @AuthenticationPrincipal User user) {

        DashboardResponseDTO response = dashboardService.getWebsiteStats(host, cutOff, user);
        return ResponseEntity.ok(response);
    }
}