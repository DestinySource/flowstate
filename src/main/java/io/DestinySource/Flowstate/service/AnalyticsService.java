package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.AnalyticsRequestDTO;
import io.DestinySource.Flowstate.dto.AnalyticsResponseDTO;
import io.DestinySource.Flowstate.exception.FlowstateExceptions;
import io.DestinySource.Flowstate.model.Analytics;
import io.DestinySource.Flowstate.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repository;

    public AnalyticsService(AnalyticsRepository repository) {
        this.repository = repository;
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

    @Transactional
    public AnalyticsResponseDTO saveEvent(AnalyticsRequestDTO dto) {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);

        if ("page_view".equals(dto.eventName())){
            boolean exists = repository.existsByVisitorIdAndEventNameAndUrlAndCreatedAtAfter(
                    dto.visitorId(),
                    dto.eventName(),
                    dto.url(),
                    threshold
            );
            if (exists) {
                return new AnalyticsResponseDTO(
                        dto.visitorId(),
                        dto.url(),
                        dto.referrer(),
                        dto.siteId(),
                        dto.eventName(),
                        dto.description(),
                        LocalDateTime.now()
                );
            }
        }

        Analytics entity = new Analytics();
        entity.setVisitorId(dto.visitorId());
        entity.setUrl(dto.url());
        entity.setReferrer(dto.referrer());
        entity.setSiteId(dto.siteId());
        entity.setEventName(dto.eventName());
        entity.setDescription(dto.description());

        return mapToResponseDTO(repository.save(entity));
    }

    private AnalyticsResponseDTO mapToResponseDTO(Analytics entity) {
        return new AnalyticsResponseDTO(
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