package com.rewards.app.reward.calculation.rules;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface RewardRuleProvider {
    int SCALE = 0;
    RoundingMode ROUNDING_MODE = RoundingMode.DOWN;
    BigDecimal calculatePoints(BigDecimal amount);
}
