package io.DestinySource.Flowstate.dto;

public record AnalyticsRequestDTO(
        String visitorId,
        String url,
        String referrer,
        String siteId,
        String eventName,
        String description
) {}