package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.AnalyticsRequestDTO;
import io.DestinySource.Flowstate.dto.AnalyticsResponseDTO;
import io.DestinySource.Flowstate.exception.FlowstateExceptions;
import io.DestinySource.Flowstate.model.Analytics;
import io.DestinySource.Flowstate.model.Site;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import io.DestinySource.Flowstate.repository.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repository;
    private final SiteRepository siteRepository;

    public AnalyticsService(AnalyticsRepository repository, SiteRepository siteRepository) {
        this.repository = repository;
        this.siteRepository = siteRepository;
    }

    @Transactional(readOnly = true)
    public List<AnalyticsResponseDTO> getAllEvents() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnalyticsResponseDTO getEventById(Long id) {
        Analytics entity = repository.findById(id)
                .orElseThrow(() -> new FlowstateExceptions.ResourceNotFound("Event " + id + " niet gevonden."));
        return mapToResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<AnalyticsResponseDTO> getEventsBySiteUrl(String siteHost) {
        return repository.findBySite_SiteHost(siteHost).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AnalyticsResponseDTO saveEvent(AnalyticsRequestDTO dto) {
        // 1. Find the site using the identifier from your request DTO (assuming dto.siteId() holds the site url string)
        Site site = siteRepository.findBySiteHost(dto.siteId())
                .orElseThrow(() -> new FlowstateExceptions.ResourceNotFound("Site " + dto.siteId() + " niet gevonden."));

        // 2. Security validation: Stop unverified platforms from logging records
        if (!site.isVerified()) {
            throw new FlowstateExceptions.UnauthorizedException("Kan data niet loggen. Site is niet geverifieerd.");
        }

        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);

        if ("page_view".equals(dto.eventName())){
            boolean exists = repository.existsByVisitorIdAndEventNameAndPathAndCreatedAtAfter(
                    dto.visitorId(),
                    dto.eventName(),
                    dto.path(),
                    threshold
            );
            if (exists) {
                return new AnalyticsResponseDTO(
                        dto.visitorId(),
                        dto.path(),
                        dto.referrer(),
                        site.getSiteHost(),
                        dto.eventName(),
                        dto.description(),
                        dto.browser(),
                        dto.os(),
                        dto.device(),
                        LocalDateTime.now()
                );
            }
        }

        Analytics entity = new Analytics();
        entity.setVisitorId(dto.visitorId());
        entity.setPath(dto.path());
        entity.setReferrer(dto.referrer());
        entity.setSite(site);
        entity.setEventName(dto.eventName());
        entity.setDescription(dto.description());
        entity.setBrowser(dto.browser());
        entity.setOs(dto.os());
        entity.setDevice(dto.device());
        return mapToResponseDTO(repository.save(entity));
    }

    private AnalyticsResponseDTO mapToResponseDTO(Analytics entity) {
        String siteIdentifier = (entity.getSite() != null) ? entity.getSite().getSiteHost() : null;

        return new AnalyticsResponseDTO(
                entity.getVisitorId(),
                entity.getPath(),
                entity.getReferrer(),
                siteIdentifier,
                entity.getEventName(),
                entity.getDescription(),
                entity.getBrowser(),
                entity.getOs(),
                entity.getDevice(),
                entity.getCreatedAt()
        );
    }
}