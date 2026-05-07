package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.AnalyticsDTO;
import io.DestinySource.Flowstate.exception.FlowstateExceptions;
import io.DestinySource.Flowstate.model.Analytics;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repository;

    public AnalyticsService(AnalyticsRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AnalyticsDTO> getAllEvents() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnalyticsDTO getEventById(Long id) {
        Analytics entity = repository.findById(id)
                .orElseThrow(() -> new FlowstateExceptions.ResourceNotFound("Event " + id + " niet gevonden."));
        return mapToDTO(entity);
    }

    @Transactional
    public AnalyticsDTO saveEvent(AnalyticsDTO dto) {
        Analytics entity = new Analytics();
        entity.setVisitorId(dto.visitorId());
        entity.setUrl(dto.url());
        entity.setReferrer(dto.referrer());
        entity.setSiteId(dto.siteId());
        entity.setEventName(dto.eventName());
        entity.setDescription(dto.description());
        return mapToDTO(repository.save(entity));
    }

    private AnalyticsDTO mapToDTO(Analytics entity) {
        return new AnalyticsDTO(
                entity.getId(),
                entity.getVisitorId(),
                entity.getUrl(),
                entity.getReferrer(),
                entity.getSiteId(),
                entity.getEventName(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}