package com.rewards.app.reward.calculation.rules;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OneHundredDollarsRuleProvider implements RewardRuleProvider {

    private static final BigDecimal ONE_DOLLAR_POINT_VALUE = BigDecimal.ONE;
    private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    @Override
    public BigDecimal calculatePoints(BigDecimal amount) {

        if (isAmountLowerThan100(amount)) {
            return isAmountGreaterThan50(amount) ? amount.subtract(FIFTY).multiply(ONE_DOLLAR_POINT_VALUE).setScale(SCALE, ROUNDING_MODE) : BigDecimal.ZERO;
        }
        return FIFTY.multiply(ONE_DOLLAR_POINT_VALUE).setScale(SCALE, ROUNDING_MODE);
    }

    private boolean isAmountLowerThan100(BigDecimal amount) {
        return amount.compareTo(ONE_HUNDRED) < 0;
    }

    private boolean isAmountGreaterThan50(BigDecimal amount) {
        return amount.compareTo(FIFTY) > 0;
    }
}
