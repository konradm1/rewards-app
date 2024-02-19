package com.rewards.app.purchase.repository;

import com.rewards.app.purchase.model.Purchase;
import com.rewards.app.purchase.PurchaseDateAndAmountRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query("""
       select new com.rewards.app.purchase.PurchaseDateAndAmountRecord(p.amount, p.createdAt) from Purchase p
       where p.customerId = :customerId
       and p.createdAt >= :startDate
       and p.currency = :currency
    """)
    List<PurchaseDateAndAmountRecord> getCustomerPurchasesForCurrencyAndStartDate(Long customerId, Currency currency, LocalDateTime startDate);
}
