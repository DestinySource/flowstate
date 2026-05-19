package io.DestinySource.Flowstate.dto;

import java.time.LocalDateTime;

public record AnalyticsResponseDTO(
        String visitorId,
        String siteId,
        String path,
        String referrer,
        String eventName,
        String description,
        LocalDateTime createdAt
) {}