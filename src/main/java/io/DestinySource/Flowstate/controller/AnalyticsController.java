package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.AnalyticsRequestDTO;
import io.DestinySource.Flowstate.dto.AnalyticsResponseDTO;
import io.DestinySource.Flowstate.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping
    public List<AnalyticsResponseDTO> getAll() {
        return service.getAllEvents();
    }
    @GetMapping("/site")
    public List<AnalyticsResponseDTO> getBySiteUrl(@RequestParam String url) {
        return service.getEventsBySiteUrl(url);
    }
    @PostMapping
    public AnalyticsResponseDTO create(@RequestBody AnalyticsRequestDTO dto) {
        return service.saveEvent(dto);
    }
    @GetMapping("/{id}")
    public AnalyticsResponseDTO getById(@PathVariable Long id) {
        return service.getEventById(id);
    }
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("FlowState Backend is LIVE!");
    }
}
