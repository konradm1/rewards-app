package com.rewards.app.reward.calculation.rules;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OneHundredDollarsRuleProviderTest {

    @InjectMocks
    private OneHundredDollarsRuleProvider ruleProvider;


    @ParameterizedTest
    @CsvSource({
            "75, 25",
            "150, 50",
            "100, 50",
            "75.5, 25"
    })
    void testCalculationPointsWhenPointsCollected(BigDecimal amountParam, BigDecimal expectedPointsParam) {
        BigDecimal expectedPoints = expectedPointsParam.setScale(OneHundredDollarsRuleProvider.SCALE, OneHundredDollarsRuleProvider.ROUNDING_MODE);
        assertEquals(expectedPoints, ruleProvider.calculatePoints(amountParam));
    }

    @ParameterizedTest
    @CsvSource({
            "50, 0",
            "48, 0",
            "50.9,0"
    })
    void testCalculationPointsWhenPointsNotCollected(BigDecimal amount, BigDecimal expectedPoints) {
        assertEquals(expectedPoints, ruleProvider.calculatePoints(amount));
    }
}
