package com.rewards.app.purchase.repository;

import com.rewards.app.BaseIT;
import com.rewards.app.purchase.model.Purchase;
import com.rewards.app.purchase.PurchaseDateAndAmountRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PurchaseRepositoryTest extends BaseIT {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setup() {
        purchaseRepository.deleteAll();
    }

    @Test
    void getCustomerPurchasesForCurrencyAndStartDateWhenNoExists() {

        // when
        List<PurchaseDateAndAmountRecord> results = purchaseRepository.getCustomerPurchasesForCurrencyAndStartDate(1L, Currency.getInstance(Locale.US), LocalDateTime.now());

        // then
        assertThat(results).isEmpty();
    }

    @Test
    void getCustomerPurchasesForCurrencyAndStartDateWhenExists() {

        Purchase purchase1 = new Purchase(1L, 3L, Currency.getInstance(Locale.US), new BigDecimal("120").setScale(2, RoundingMode.HALF_DOWN), LocalDateTime.now().minusMonths(1), 0L);
        purchaseRepository.save(purchase1);
        Purchase purchase2 = new Purchase(2L, 3L, Currency.getInstance(Locale.US), new BigDecimal("120").setScale(2, RoundingMode.HALF_DOWN), LocalDateTime.now().minusMonths(3), 0L);
        purchaseRepository.save(purchase2);

        // when
        List<PurchaseDateAndAmountRecord> results = purchaseRepository.getCustomerPurchasesForCurrencyAndStartDate(3L, Currency.getInstance(Locale.US), LocalDateTime.now().minusMonths(2));

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).amount()).isEqualTo(purchase1.getAmount());
        assertThat(results.get(0).date().withNano(0)).isEqualTo(purchase1.getCreatedAt().withNano(0));
    }

}
