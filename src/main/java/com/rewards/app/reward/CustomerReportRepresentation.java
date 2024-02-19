package com.rewards.app.reward;

import lombok.Builder;
import java.math.BigDecimal;
import java.util.Map;

@Builder
public record CustomerReportRepresentation(Map<String, BigDecimal> pointsByMonth, BigDecimal totalPoints) {
}
