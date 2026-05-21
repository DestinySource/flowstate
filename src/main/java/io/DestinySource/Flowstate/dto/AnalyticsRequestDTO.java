package io.DestinySource.Flowstate.dto;

public record AnalyticsRequestDTO(
        String visitorId,
        String siteId,
        String path,
        String referrer,
        String eventName,
        String description,
        String browser,
        String os,
        String device
) {}