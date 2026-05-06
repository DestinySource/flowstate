package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.AnalyticsDTO;
import io.DestinySource.Flowstate.model.Analytics;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import io.DestinySource.Flowstate.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<AnalyticsDTO> getAll() {
        return service.getAllEvents();
    }

    @PostMapping
    public AnalyticsDTO create(@RequestBody AnalyticsDTO dto) {
        return service.saveEvent(dto);
    }
    @GetMapping("/{id}")
    public AnalyticsDTO getById(@PathVariable Long id) {
        return service.getEventById(id);
    }
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("FlowState Backend is LIVE!");
    }
}
