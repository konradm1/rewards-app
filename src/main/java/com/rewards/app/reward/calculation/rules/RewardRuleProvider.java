package com.rewards.app.reward.calculation.rules;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface RewardRuleProvider {
    int SCALE = 2;
    RoundingMode ROUNDING_MODE = RoundingMode.HALF_DOWN;
    BigDecimal calculatePoints(BigDecimal amount);
}
