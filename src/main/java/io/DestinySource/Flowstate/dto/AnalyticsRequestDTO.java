package io.DestinySource.Flowstate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AnalyticsRequestDTO(
        @NotBlank(message = "Visitor ID may never be empty")
        String visitorId,
        @NotBlank(message = "Site ID (host) is needed")
        String siteId,
        @NotBlank(message = "Path may not be empty")
        String path,
        String referrer,
        @NotBlank(message = "Event name mag niet leeg zijn")
        @Size(max = 50, message = "Event name mag maximaal 50 tekens lang zijn")
        String eventName,
        String description,
        String browser,
        String os,
        String device
) {}