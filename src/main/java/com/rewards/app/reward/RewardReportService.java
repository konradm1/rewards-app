package com.rewards.app.reward;

import com.rewards.app.purchase.PurchaseService;
import com.rewards.app.purchase.PurchaseDateAndAmountRecord;
import com.rewards.app.reward.calculation.RewardCalculationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RewardReportService {
    public static final DateTimeFormatter YEAR_MONTH_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final int NUMBER_BACKWARD_MONTHS = 2;
    private final PurchaseService purchaseService;
    private final RewardCalculationService rewardCalculationService;

    public CustomerReportRepresentation prepareReportForCustomer(Long customerId) {
        LocalDateTime reportStartDate = prepareReportStartDate();
       List<PurchaseDateAndAmountRecord> purchaseRecordsFromLastThreeMonths = purchaseService.providePurchaseRecordsForCustomer(customerId, Currency.getInstance(Locale.US), reportStartDate);
       Map<String, BigDecimal> rewardPointsByMonth = prepareRewardPointsByMonth(purchaseRecordsFromLastThreeMonths);

       return CustomerReportRepresentation.builder()
               .pointsByMonth(rewardPointsByMonth)
               .totalPoints(rewardPointsByMonth.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add))
               .build();
    }

    private LocalDateTime prepareReportStartDate() {
        LocalDateTime currentMonthFirstDay = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return currentMonthFirstDay.minusMonths(NUMBER_BACKWARD_MONTHS);
    }

    private Map<String, BigDecimal> prepareRewardPointsByMonth(List<PurchaseDateAndAmountRecord> purchaseRecords) {
        Map<String, BigDecimal> rewardsByMonth = new HashMap<>();
        Map<String, List<PurchaseDateAndAmountRecord>> purchaseRecordsByMonth = purchaseRecords.stream()
                .collect(Collectors.groupingBy(purchase -> purchase.date().format(YEAR_MONTH_PATTERN)));

        purchaseRecordsByMonth.forEach((month, purchases) -> rewardsByMonth.put(month, rewardCalculationService.calculatePoints(purchases)));

        return rewardsByMonth;
    }
}
