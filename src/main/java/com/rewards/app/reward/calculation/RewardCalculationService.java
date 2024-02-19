package com.rewards.app.reward.calculation;

import com.rewards.app.purchase.PurchaseDateAndAmountRecord;
import com.rewards.app.reward.calculation.rules.RewardRuleProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class RewardCalculationService {
    private List<RewardRuleProvider> rewardRuleProviders;

    public BigDecimal calculatePoints(List<PurchaseDateAndAmountRecord> purchases) {
        return purchases.stream()
                .map(this::calculatePointsForSinglePurchase)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePointsForSinglePurchase(PurchaseDateAndAmountRecord purchaseRecord) {
        return rewardRuleProviders.stream()
                .map(rewardRule -> rewardRule.calculatePoints(purchaseRecord.amount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
