package com.rewards.app.reward.calculation;

import com.rewards.app.purchase.PurchaseDateAndAmountRecord;
import com.rewards.app.reward.calculation.rules.RewardRuleProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardCalculationServiceTest {

    private RewardCalculationService rewardCalculationService;

    @Mock
    private RewardRuleProvider mockRuleProvider1;
    @Mock
    private RewardRuleProvider mockRuleProvider2;

    @BeforeEach
    void setup() {
        rewardCalculationService = new RewardCalculationService(List.of(mockRuleProvider1, mockRuleProvider2));
    }

    @Test
    void testCalculatePoints() {
        // given
        when(mockRuleProvider1.calculatePoints(any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        when(mockRuleProvider2.calculatePoints(any(BigDecimal.class))).thenReturn(BigDecimal.ONE);
        PurchaseDateAndAmountRecord mockRecord = mock(PurchaseDateAndAmountRecord.class);
        when(mockRecord.amount()).thenReturn(BigDecimal.valueOf(100));

        // when
        BigDecimal calculationResults = rewardCalculationService.calculatePoints(List.of(mockRecord, mockRecord));

        // then
        BigDecimal expectedPoints = BigDecimal.valueOf(22);
        assertEquals(expectedPoints, calculationResults);
    }

    @Test
    void testCalculatePointsWhenNoData() {
        // when
        BigDecimal calculationResults = rewardCalculationService.calculatePoints(List.of());

        // then
        BigDecimal expectedPoints = BigDecimal.ZERO;
        assertEquals(expectedPoints, calculationResults);
    }
}
