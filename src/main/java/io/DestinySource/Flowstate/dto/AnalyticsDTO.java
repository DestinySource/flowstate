package io.DestinySource.Flowstate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDTO {
    private Long id;
    private String eventName;
    private String description;
    private LocalDateTime createdAt;
}