package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.RegisterSiteRequestDTO;
import io.DestinySource.Flowstate.model.Site;
import io.DestinySource.Flowstate.repository.SiteRepository;
import io.DestinySource.Flowstate.exception.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SiteService {
    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository){
        this.siteRepository = siteRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Site registerNewSite(RegisterSiteRequestDTO request) {
        if (siteRepository.existsBySiteHost(request.sitename())) {
            throw new BadRequestException("Deze site is al in gebruik.");
        }

        Site site = new Site();
        site.setSiteHost(request.sitename());
        site.setVerified(true);
        return siteRepository.save(site);
    }
}
