package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.model.Analytics;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AnalyticsController {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @GetMapping("/analytics")
    public List<Analytics> getAll() {
        return analyticsRepository.findAll();
    }
    @PostMapping("/analytics")
    public Analytics create(@RequestBody Analytics analytics) {
        return analyticsRepository.save(analytics);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("FlowState Backend is LIVE!");
    }
}
