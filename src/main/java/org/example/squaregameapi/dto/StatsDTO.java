package org.example.squaregameapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {
    private Long id;
    private Long gameId;
    private Long userId;
    private String outcome;
    private LocalDateTime createdAt;
}
