package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.AnalyticsItemProjection;
import io.DestinySource.Flowstate.service.DashboardService;
import io.DestinySource.Flowstate.exception.FlowstateExceptions;
import org.springframework.http.HttpStatus;
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
        try {
            Map<String, List<AnalyticsItemProjection>> response = dashboardService.getDashboardStats(host, cutOff);
            return ResponseEntity.ok(response);

        } catch (FlowstateExceptions.ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (FlowstateExceptions.UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Er is een interne fout opgetreden bij het ophalen van de statistieken.");
        }
    }
}