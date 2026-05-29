package io.DestinySource.Flowstate.dto;

import java.util.List;

public record DashboardResponseDTO(
        String host,
        boolean isVerified,
        List<AnalyticsItemProjection> pages,
        List<AnalyticsItemProjection> browser,
        List<AnalyticsItemProjection> os,
        List<AnalyticsItemProjection> device
) {}