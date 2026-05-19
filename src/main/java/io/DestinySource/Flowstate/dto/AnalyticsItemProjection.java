package io.DestinySource.Flowstate.dto;

public interface AnalyticsItemProjection {
    String getName();
    Long getUv();

    default Double getRevenue() { return 0.0; }
    default Long getPaymentCount() { return 0L; }
}