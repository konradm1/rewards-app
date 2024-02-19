package com.rewards.app.purchase;

import com.rewards.app.purchase.exception.PurchaseNotFoundException;
import com.rewards.app.purchase.model.Purchase;
import com.rewards.app.purchase.repository.PurchaseRepository;
import com.rewards.app.purchase.rest.PurchasePayload;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    public List<PurchaseDateAndAmountRecord> providePurchaseRecordsForCustomer(Long customerId, Currency currency, int backwardMonthsNumber) {
        LocalDateTime startDate = prepareReportStartDate(backwardMonthsNumber);
        return purchaseRepository.getCustomerPurchasesForCurrencyAndStartDate(customerId, currency, startDate);
    }

    private LocalDateTime prepareReportStartDate(int backwardMonthsNumber) {
        LocalDateTime currentMonthFirstDay = LocalDateTime.now().withDayOfMonth(1);
        return currentMonthFirstDay.minusMonths(backwardMonthsNumber);
    }

    @Transactional
    public void addPurchase(PurchasePayload purchasePayload) {

        Purchase purchase = Purchase.builder()
                .customerId(purchasePayload.getCustomerId())
                .currency(Currency.getInstance(Locale.US))
                .amount(purchasePayload.getAmount())
                .createdAt(purchasePayload.getCreatedAt())
                .build();

        purchaseRepository.save(purchase);

        log.trace("Purchase successfully created");
    }

    @Transactional
    public void updatePurchase(PurchasePayload purchasePayload, Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new PurchaseNotFoundException(id));

        purchase.setCustomerId(purchasePayload.getCustomerId());
        purchase.setCurrency(Currency.getInstance(Locale.US));
        purchase.setAmount(purchasePayload.getAmount());
        purchase.setCreatedAt(purchasePayload.getCreatedAt());

        purchaseRepository.save(purchase);

        log.trace("Purchase successfully updated");
    }
}
