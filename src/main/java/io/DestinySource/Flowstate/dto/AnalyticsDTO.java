package io.DestinySource.Flowstate.dto;

import java.time.LocalDateTime;

public record AnalyticsDTO(
    Long id,
    String visitorId,
    String url,
    String referrer,
    String siteId,
    String eventName,
    String description,
    LocalDateTime createdAt
) {}