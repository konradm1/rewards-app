package com.rewards.app.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseDateAndAmountRecord(BigDecimal amount, LocalDateTime date) {}
