package com.rewards.app.reward.calculation.rules;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OverOneHundredDollarsRuleProvider implements RewardRuleProvider {
    private static final BigDecimal ONE_DOLLAR_POINT_VALUE = BigDecimal.valueOf(2);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    @Override
    public BigDecimal calculatePoints(BigDecimal amount) {
        if (isAmountGreaterThan100(amount)) {
            return amount.subtract(ONE_HUNDRED).multiply(ONE_DOLLAR_POINT_VALUE).setScale(SCALE, ROUNDING_MODE);
        }
        return BigDecimal.ZERO;
    }

    private boolean isAmountGreaterThan100(BigDecimal amount) {
        return amount.compareTo(ONE_HUNDRED) > 0;
    }
}
