package com.rewards.app.purchase.model;

import com.rewards.app.purchase.converter.CurrencyConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
@EqualsAndHashCode(of = {"id"})
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long customerId;
    @NotNull
    @Convert(converter = CurrencyConverter.class)
    private Currency currency;
    @Column(precision=6, scale=2)
    @Positive
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDateTime createdAt;
    @Version
    private Long version;
}
