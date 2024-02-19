package com.rewards.app.reward.calculation.rules;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OverOneHundredDollarsRuleProviderTest {

    @InjectMocks
    private OverOneHundredDollarsRuleProvider ruleProvider;


    @ParameterizedTest
    @CsvSource({
            "150, 100"
    })
    void testCalculationPointsWhenPointsCollected(BigDecimal amountParam, BigDecimal expectedPointsParam) {
        BigDecimal expectedPoints = expectedPointsParam.setScale(OneHundredDollarsRuleProvider.SCALE, OneHundredDollarsRuleProvider.ROUNDING_MODE);
        assertEquals(expectedPoints, ruleProvider.calculatePoints(amountParam));
    }

    @ParameterizedTest
    @CsvSource({
            "75, 0",
            "100, 0",
            "100.5, 0"
    })
    void testCalculationPointsWhenPointsNotCollected(BigDecimal amount, BigDecimal expectedPoints) {
        assertEquals(expectedPoints, ruleProvider.calculatePoints(amount));
    }
}
