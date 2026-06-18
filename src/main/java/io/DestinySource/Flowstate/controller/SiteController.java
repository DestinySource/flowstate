package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.RegisterSiteRequestDTO;
import io.DestinySource.Flowstate.model.Site;
import io.DestinySource.Flowstate.service.SiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/register")
    public ResponseEntity<Site> registerSite(@Valid @RequestBody RegisterSiteRequestDTO request) {
        Site createdSite = siteService.registerNewSite(request);
        return new ResponseEntity<>(createdSite, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
            siteService.deleteSite(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}