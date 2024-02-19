package com.rewards.app.reward;

import com.rewards.app.purchase.PurchaseService;
import com.rewards.app.purchase.PurchaseDateAndAmountRecord;
import com.rewards.app.reward.calculation.RewardCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.rewards.app.reward.RewardReportService.YEAR_MONTH_PATTERN;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardReportServiceTest {
    @Mock
    private PurchaseService purchaseService;
    @Mock
    private RewardCalculationService rewardCalculationService;
    @InjectMocks
    private RewardReportService rewardReportService;

    @Test
    void prepareReportForCustomerWhenDataExists() {
        // given
        LocalDateTime today = LocalDateTime.now();
        List<PurchaseDateAndAmountRecord> purchaseDateAndAmountRecords = List.of(new PurchaseDateAndAmountRecord(BigDecimal.valueOf(120), today));
        BigDecimal calculatedPoints = new BigDecimal("90");
        when(purchaseService.providePurchaseRecordsForCustomer(1L, Currency.getInstance(Locale.US), 2))
                .thenReturn(purchaseDateAndAmountRecords);
        when(rewardCalculationService.calculatePoints(purchaseDateAndAmountRecords)).thenReturn(calculatedPoints);

        // when
        CustomerReportRepresentation report = rewardReportService.prepareReportForCustomer(1L);

        // then
        assertThat(report.totalPoints()).isEqualTo(calculatedPoints);
        assertThat(report.pointsByMonth()).containsExactly(entry(today.format(YEAR_MONTH_PATTERN), calculatedPoints));
    }

    @Test
    void prepareReportForCustomerWhenDataNotExists() {
        // given
        List<PurchaseDateAndAmountRecord> purchaseDateAndAmountRecords = List.of();
        BigDecimal calculatedPoints = BigDecimal.ZERO;
        when(purchaseService.providePurchaseRecordsForCustomer(1L, Currency.getInstance(Locale.US), 2))
                .thenReturn(purchaseDateAndAmountRecords);

        // when
        CustomerReportRepresentation report = rewardReportService.prepareReportForCustomer(1L);

        // then
        assertThat(report.totalPoints()).isEqualTo(calculatedPoints);
        assertThat(report.pointsByMonth()).isEmpty();
    }
}
